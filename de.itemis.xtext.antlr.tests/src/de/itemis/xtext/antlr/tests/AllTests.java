/*******************************************************************************
 * Copyright (c) 2009 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package de.itemis.xtext.antlr.tests;

import junit.framework.Test;
import junit.framework.TestSuite;
import de.itemis.xtext.antlr.AntlrGrammarGenUtilTest;
import de.itemis.xtext.antlr.AntlrLexerSplitterTest;
import de.itemis.xtext.antlr.AntlrParserSplitterTest;
import de.itemis.xtext.antlr.TerminalRuleToLexerBodyTest;
import de.itemis.xtext.antlr.ex.common.KeywordHelperTest;

/**
 * @author huebner - Initial contribution and API
 */
public class AllTests {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for de.itemis.xtext.antlr.tests");
		suite.addTestSuite(AntlrGrammarGenUtilTest.class);
		suite.addTestSuite(TerminalRuleToLexerBodyTest.class);
		suite.addTestSuite(KeywordHelperTest.class);
		suite.addTestSuite(AntlrLexerSplitterTest.class);
		suite.addTestSuite(AntlrParserSplitterTest.class);
		return suite;
	}
}
