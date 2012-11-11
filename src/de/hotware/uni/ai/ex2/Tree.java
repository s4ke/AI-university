/**
 * File Tree.java
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

import de.hotware.util.BaseEvent;

public interface Tree<T> {
	
	/**
	 * @return the node that was inserted
	 */
	public Node<T> insert(Node<T> pAt, Node<T> pNode);
	
	public void setRoot(Node<T> pRoot);
	
	public Node<T> getRoot();
	
	public void depthFirstSearch(TraversionListener<T> pTraversionListener);
	
	public void breadthFirstSearch(TraversionListener<T> pTraversionListener);
	
	public static interface TraversionListener<V> {
		
		/**
		 * @return false if traversion should stop
		 * @throws Exception 
		 */
		public boolean onVisit(VisitEvent<V> pEvent);
		
		public static class VisitEvent<W> extends BaseEvent<Tree<W>> {

			protected Node<W> mNode;
			
			public VisitEvent(Tree<W> pSource, Node<W> pNode) {
				super(pSource);
				this.mNode = pNode;
			}
			
			public Node<W> getNode() {
				return this.mNode;
			}
			
		}
		
	}

}
