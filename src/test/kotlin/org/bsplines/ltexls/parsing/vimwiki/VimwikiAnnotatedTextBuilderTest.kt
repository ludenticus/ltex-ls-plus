package org.bsplines.ltexls.parsing.vimwiki

import org.bsplines.ltexls.parsing.CodeAnnotatedTextBuilderTest
import kotlin.test.Test

class VimwikiAnnotatedTextBuilderTest :
  CodeAnnotatedTextBuilderTest("vimwiki") {

  @Test
  fun testBold() {
    assertPlainText(
      "*bold* text",
      "bold text",
    )
  }

  @Test
  fun testItalic() {
    assertPlainText(
      "_italic_ text",
      "italic text",
    )
  }

}

