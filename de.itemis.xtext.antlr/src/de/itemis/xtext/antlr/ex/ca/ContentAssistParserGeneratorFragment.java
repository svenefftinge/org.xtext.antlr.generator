/*******************************************************************************
 * Copyright (c) 2009 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package de.itemis.xtext.antlr.ex.ca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.eclipse.xpand2.XpandExecutionContext;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.Alternatives;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.EnumRule;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.Group;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.generator.BindFactory;
import org.eclipse.xtext.generator.Binding;
import org.eclipse.xtext.generator.Generator;

import de.itemis.xtext.antlr.AbstractAntlrGeneratorFragment;
import de.itemis.xtext.antlr.AntlrToolRunner;
import de.itemis.xtext.antlr.ex.common.KeywordHelper;

/**
 * A {@link IGeneratorFragment} to generate a lightweight AntLR based parser used in content assist.
 * 
 * @author Sebastian Zarnekow - Initial contribution and API
 */
public class ContentAssistParserGeneratorFragment extends AbstractAntlrGeneratorFragment {

	@Override
	public void generate(Grammar grammar, XpandExecutionContext ctx) {
		KeywordHelper helper = new KeywordHelper(grammar);
		super.generate(grammar, ctx);
		final String srcGenPath = ctx.getOutput().getOutlet(Generator.SRC_GEN_UI).getPath();
		String libPath = srcGenPath + "/" + getLexerGrammarFileName(grammar).replace('.', '/');
		libPath = libPath.substring(0, libPath.lastIndexOf('/'));
		AntlrToolRunner.runWithParams(srcGenPath+"/"+getLexerGrammarFileName(grammar).replace('.', '/')+".g");
		AntlrToolRunner.runWithParams(srcGenPath+"/"+getParserGrammarFileName(grammar).replace('.', '/')+".g", "-lib", libPath);
		helper.discardHelper(grammar);
	}

	@Override
	public Set<Binding> getGuiceBindingsUi(Grammar grammar) {
		return new BindFactory().addTypeToType(
				"org.eclipse.xtext.ui.core.editor.contentassist.ContentAssistContext.Factory",
				"org.eclipse.xtext.ui.common.editor.contentassist.antlr.ParserBasedContentAssistContextFactory")
				.addTypeToType("org.eclipse.xtext.ui.common.editor.contentassist.antlr.IContentAssistParser",
						getParserClassName(grammar)).getBindings();
	}

	@Override
	public String[] getRequiredBundlesUi(Grammar grammar) {
		return new String[] { "org.antlr.runtime" };
	}

	@Override
	public String[] getExportedPackagesUi(Grammar grammar) {
		return new String[] { GrammarUtil.getNamespace(grammar) + ".contentassist.antlr" };
	}

	public static String getParserClassName(Grammar g) {
		return GrammarUtil.getNamespace(g) + ".contentassist.antlr." + GrammarUtil.getName(g) + "Parser";
	}

	public static String getInternalLexerClassName(Grammar g) {
		return getLexerGrammarFileName(g) + "Lexer";
	}

	public static String getInternalParserClassName(Grammar g) {
		return getParserGrammarFileName(g) + "Parser";
	}

	public static String getLexerGrammarFileName(Grammar g) {
		return GrammarUtil.getNamespace(g) + ".contentassist.antlr.lexer.Internal" + GrammarUtil.getName(g);
	}
	
	public static String getParserGrammarFileName(Grammar g) {
		return GrammarUtil.getNamespace(g) + ".contentassist.antlr.internal.Internal" + GrammarUtil.getName(g);
	}

	public static Collection<Alternatives> getAllAlternatives(Grammar g) {
		return getAllElementsByType(g, Alternatives.class);
	}

	public static Collection<Group> getAllGroups(Grammar g) {
		return getAllElementsByType(g, Group.class);
	}
	
	public static Collection<Assignment> getAllAssignments(Grammar g) {
		return getAllElementsByType(g, Assignment.class);
	}

	private static <T extends AbstractElement> Collection<T> getAllElementsByType(Grammar g, Class<T> type) {
		Collection<ParserRule> allParserRules = GrammarUtil.allParserRules(g);
		List<T> result = new ArrayList<T>(30);
		for (ParserRule rule : allParserRules) {
			result.addAll(EcoreUtil2.getAllContentsOfType(rule, type));
		}
		Collection<EnumRule> allEnumRules = GrammarUtil.allEnumRules(g);
		for (EnumRule rule : allEnumRules) {
			result.addAll(EcoreUtil2.getAllContentsOfType(rule, type));
		}
		return result;
	}
}