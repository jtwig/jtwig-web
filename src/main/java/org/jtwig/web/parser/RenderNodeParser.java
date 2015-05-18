package org.jtwig.web.parser;

import com.google.common.base.Optional;
import org.jtwig.parser.parboiled.ParserContext;
import org.jtwig.parser.parboiled.base.LexicParser;
import org.jtwig.parser.parboiled.base.LimitsParser;
import org.jtwig.parser.parboiled.base.PositionTrackerParser;
import org.jtwig.parser.parboiled.base.SpacingParser;
import org.jtwig.parser.parboiled.expression.AnyExpressionParser;
import org.jtwig.parser.parboiled.model.Keyword;
import org.jtwig.parser.parboiled.node.AddonParser;
import org.jtwig.web.parser.model.RenderNode;
import org.parboiled.Rule;

public class RenderNodeParser extends AddonParser {
    public static final String RENDER_KEYWORD = "render";

    public RenderNodeParser(ParserContext context) {
        super(RenderNodeParser.class, context);
    }

    @Override
    public Rule NodeRule() {
        PositionTrackerParser positionTrackerParser = parserContext().parser(PositionTrackerParser.class);
        LimitsParser limitsParser = parserContext().parser(LimitsParser.class);
        SpacingParser spacingParser = parserContext().parser(SpacingParser.class);
        AnyExpressionParser anyExpressionParser = parserContext().parser(AnyExpressionParser.class);
        LexicParser lexicParser = parserContext().parser(LexicParser.class);
        return Sequence(
                positionTrackerParser.PushPosition(),
                limitsParser.startCode(),
                spacingParser.Spacing(),
                lexicParser.Keyword(RENDER_KEYWORD),
                spacingParser.Mandatory(),

                Mandatory(anyExpressionParser.ExpressionRule(), "Expecting path expression"),
                spacingParser.Spacing(),

                FirstOf(
                        Sequence(
                                lexicParser.Keyword(Keyword.WITH),
                                spacingParser.Spacing(),
                                Mandatory(anyExpressionParser.ExpressionRule(), "Expecting with expression")
                        ),
                        anyExpressionParser.push(null)
                ),

                limitsParser.endCode(),

                push(new RenderNode(positionTrackerParser.pop(2), anyExpressionParser.pop(1), Optional.fromNullable(anyExpressionParser.pop())))
        );
    }
}
