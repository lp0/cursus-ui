/*
	cursus - Race series management program
	Copyright 2011  Simon Arlott

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.lp0.cursus.ui.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import eu.lp0.cursus.db.data.Class;
import eu.lp0.cursus.db.data.Series;
import eu.lp0.cursus.ui.component.HierarchicalTreeBranch;
import eu.lp0.cursus.ui.component.HierarchicalTreeRoot;

public class ClassListTreeNode extends HierarchicalTreeRoot<Series, Class, ClassTreeNode> implements HierarchicalTreeBranch<Series, Class> {
	public ClassListTreeNode() {
		super(null);
	}

	public ClassListTreeNode(Series series) {
		super(series);
		for (Class clazz : getChildItems(series)) {
			add(new ClassTreeNode(clazz));
		}
	}

	@Override
	protected ClassTreeNode constructChildNode(Class clazz) {
		return new ClassTreeNode(clazz);
	}

	@Override
	public List<Class> getChildItems(Series series) {
		List<Class> classes = new ArrayList<Class>(series.getClasses());
		Collections.sort(classes);
		return classes;
	}
}