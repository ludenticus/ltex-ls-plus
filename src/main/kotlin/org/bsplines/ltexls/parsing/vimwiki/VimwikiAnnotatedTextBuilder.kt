package org.bsplines.ltexls.parsing.vimwiki

import org.bsplines.ltexls.parsing.CharacterBasedCodeAnnotatedTextBuilder

class VimwikiAnnotatedTextBuilder(
    codeLanguageId: String,
) : CharacterBasedCodeAnnotatedTextBuilder(codeLanguageId) {

    private var lineBuffer = StringBuilder()
    private var atLineStart = true

    override fun processCharacter() {
        lineBuffer.append(curString)

        // Only check for header at start of line
        if (atLineStart) {
            val line = lineBuffer.toString()
            val headerMatch = HEADER_REGEX.matchEntire(line.trim())
            if (headerMatch != null) {
                addText(headerMatch.groups["text"]?.value)
                addMarkup(".", null) // Add a dot at end of heading
                characterProcessed = true
                lineBuffer.clear()
                atLineStart = false
                return
            }
        }

        // Inline markup (* or _)
        addMarkup(INLINE_MARKUP_REGEX)

        // Default: literal character
        addText(curString)

        // Detect new line
        atLineStart = curString == "\n"
        if (atLineStart) lineBuffer.clear()
    }

    override fun addText(text: String?): CharacterBasedCodeAnnotatedTextBuilder {
        if (characterProcessed) return this
        return super.addText(text)
    }
    override fun addMarkup(markup: String?, interpretAs: String?): CharacterBasedCodeAnnotatedTextBuilder {
        if (characterProcessed) return this
        return super.addMarkup(markup, interpretAs)
    }

    companion object {
        private val HEADER_REGEX = Regex("^(?<start>=+)\\s*(?<text>.+?)\\s*(?<end>=+)?$")
        private val INLINE_MARKUP_REGEX = Regex("^(\\*|_)")
    }
}
