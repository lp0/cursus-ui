/*
	cursus - Race series management program
	Copyright 2011, 2014  Simon Arlott

	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU Affero General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU Affero General Public License for more details.

	You should have received a copy of the GNU Affero General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.lp0.cursus.ui.race;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import eu.lp0.cursus.db.data.Race;
import eu.lp0.cursus.ui.component.AbstractDatabaseTab;
import eu.lp0.cursus.ui.component.DatabaseWindow;

public class RaceLapsTab extends AbstractDatabaseTab<Race> {
	private JScrollPane scrollPane;
	private JTable table;

	public RaceLapsTab(DatabaseWindow win) {
		super(Race.class, win, "tab.laps"); //$NON-NLS-1$
		initialise();
	}

	private void initialise() {
		setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);
	}

	@Override
	public void tabRefresh(Race race) {

	}

	@Override
	public void tabClear() {

	}
}