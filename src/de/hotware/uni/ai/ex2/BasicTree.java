/**
 * File BasicTree.java
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

import java.util.LinkedList;
import java.util.Queue;

import de.hotware.uni.ai.ex2.Tree.TraversionListener.VisitEvent;

public class BasicTree<T> implements Tree<T> {

	protected Node<T> mRoot;
	protected TraversionListener<T> mTraversionListener;

	@Override
	public Node<T> insert(Node<T> pAt, Node<T> pNode) {
		if(pAt == pNode) {
			throw new IllegalArgumentException(
					"pAt may not be the same as pNode");
		}
		for(Node<T> cur : pAt.children()) {
			cur.addSibling(pNode);
		}
		pAt.addChild(pNode);
		return pNode;
	}

	@Override
	public void setRoot(Node<T> pRoot) {
		this.mRoot = pRoot;
	}

	public Node<T> getRoot() {
		return this.mRoot;
	}
	
	@Override
	public void depthFirstSearch(TraversionListener<T> pTraversionListener) {
		this.mRoot.depthFirstSearch(this, pTraversionListener);
	}
	
	@Override
	public void breadthFirstSearch(TraversionListener<T> pTraversionListener) {
		Queue<Node<T>> queue = new LinkedList<Node<T>>();
		queue.add(this.mRoot);
		while(!queue.isEmpty()) {
			Node<T> node = queue.poll();
			if(pTraversionListener.onVisit(new VisitEvent<T>(this, node))) {
				for(Node<T> child : node.children()) {
					queue.add(child);
				}
			}
		}
		
	}

}
