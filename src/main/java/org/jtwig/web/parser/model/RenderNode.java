package org.jtwig.web.parser.model;

import com.google.common.base.Optional;
import org.jtwig.context.RenderContext;
import org.jtwig.context.model.EscapeMode;
import org.jtwig.model.expression.Expression;
import org.jtwig.model.position.Position;
import org.jtwig.model.tree.Node;
import org.jtwig.render.RenderException;
import org.jtwig.render.Renderable;
import org.jtwig.render.impl.StringRenderable;
import org.jtwig.web.servlet.ServletRequestHolder;
import org.jtwig.web.servlet.ServletResponseHolder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class RenderNode extends Node {
    private final Expression pathExpression;
    private final Optional<Expression> withExpression;

    public RenderNode(Position position, Expression pathExpression, Optional<Expression> withExpression) {
        super(position);
        this.pathExpression = pathExpression;
        this.withExpression = withExpression;
    }

    @Override
    public Renderable render(RenderContext renderContext) {
        String path = pathExpression.calculate(renderContext).asString();
        HttpServletRequest httpServletRequest = getHttpServletRequest();

        if (withExpression.isPresent()) {
            Map<Object, Object> map = withExpression.get().calculate(renderContext).asMap();
            for (Map.Entry<Object, Object> entry : map.entrySet()) {
                if (entry.getKey() != null) {
                    httpServletRequest.setAttribute(entry.getKey().toString(), entry.getValue());
                }
            }
        }

        InMemoryHttpServletResponse response = new InMemoryHttpServletResponse(getHttpServletResponse());

        try {
            httpServletRequest.getRequestDispatcher(path).include(httpServletRequest, response);
            return new StringRenderable(new String(response.getContent()), EscapeMode.NONE);
        } catch (ServletException | IOException e) {
            throw new RenderException(e);
        }
    }

    protected HttpServletResponse getHttpServletResponse() {
        return ServletResponseHolder.get();
    }

    protected HttpServletRequest getHttpServletRequest() {
        return ServletRequestHolder.get();
    }

    public static class InMemoryHttpServletResponse extends HttpServletResponseWrapper {
        private final InMemoryServletOutputStream outputStream = new InMemoryServletOutputStream();
        private final PrintWriter writer = new PrintWriter(outputStream);

        /**
         * Constructs a response adaptor wrapping the given response.
         *
         * @param response
         * @throws IllegalArgumentException if the response is null
         */
        public InMemoryHttpServletResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return outputStream;
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            return writer;
        }

        public byte[] getContent () {
            return outputStream.getMemory();
        }
    }

    public static class InMemoryServletOutputStream extends ServletOutputStream {
        private ByteArrayOutputStream memory;

        public InMemoryServletOutputStream() {
            this.memory = new ByteArrayOutputStream(1024);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }

        @Override
        public void write(int b) throws IOException {
            memory.write(b);
        }

        public byte[] getMemory() {
            return memory.toByteArray();
        }

    }
}
