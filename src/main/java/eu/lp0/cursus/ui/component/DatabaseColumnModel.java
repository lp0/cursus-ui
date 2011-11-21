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
package eu.lp0.cursus.ui.component;

import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import eu.lp0.cursus.db.data.AbstractEntity;

public abstract class DatabaseColumnModel<T extends AbstractEntity, V> {
	protected boolean isCellEditable() {
		return false;
	}

	public void setupEditableModel(TableColumn col) {
		col.setCellRenderer(createCellRenderer());
	}

	public abstract String getColumnName();

	protected abstract V getValue(T row);

	protected abstract TableCellRenderer createCellRenderer();
}