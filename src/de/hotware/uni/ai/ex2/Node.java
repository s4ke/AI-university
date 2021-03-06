/**
 * File Node.java
 * ---------------------------------------------------------
 *
 * Copyright (C) 2012 Martin Braun (martinbraun123@aol.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * - The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * - The origin of the software must not be misrepresented.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * TL;DR: As long as you clearly give me credit for this Software, you are free to use as you like, even in commercial software, but don't blame me
 *   if it breaks something.
 */
package de.hotware.uni.ai.ex2;

import java.util.List;

import de.hotware.uni.ai.ex2.Tree.TraversionListener;

public interface Node<T> {
	
	public T get();
	
	public List<Node<T>> children();
	
	public List<Node<T>> siblings();
	
	public Node<T> parent();
	
	/**
	 * @return reference to this object, for chaining
	 */
	public Node<T> addChild(Node<T> pNode);
	
	/**
	 * @return reference to this object, for chaining
	 */
	public Node<T> addChild(T pElement);
	
	public Node<T> addSibling(Node<T> pNode);

	void depthFirstSearch(Tree<T> pTree,
			TraversionListener<T> pTraversionListener);
	
}