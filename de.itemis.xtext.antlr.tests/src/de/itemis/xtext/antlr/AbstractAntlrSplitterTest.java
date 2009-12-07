/*******************************************************************************
 * Copyright (c) 2008 itemis AG (http://www.itemis.eu) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************/
package de.itemis.xtext.antlr;

import org.antlr.Tool;
import org.eclipse.xtext.junit.AbstractXtextTests;

/**
 *
 * @author Heiko Behrens - Initial contribution and API
 */
public abstract class AbstractAntlrSplitterTest extends AbstractXtextTests {

	public void testAntlrVersion() throws Exception {
		assertEquals("Please verify output manually, algorthims expect version-dependent code layout.",
				"3.0.1", Tool.VERSION);
	}

}