package org.jtwig.web.parser.addon;

import org.jtwig.parser.addon.AddonParserProvider;
import org.jtwig.parser.parboiled.node.AddonParser;
import org.jtwig.web.parser.parboiled.RenderNodeParser;

import java.util.Collection;

import static java.util.Arrays.asList;

public class RenderNodeAddonProvider implements AddonParserProvider {
    @Override
    public Class<? extends AddonParser> parser() {
        return RenderNodeParser.class;
    }

    @Override
    public Collection<String> keywords() {
        return asList(RenderNodeParser.RENDER_KEYWORD);
    }
}
