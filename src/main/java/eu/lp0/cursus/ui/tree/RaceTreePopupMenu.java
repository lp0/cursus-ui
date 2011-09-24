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

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import eu.lp0.cursus.db.data.Race;
import eu.lp0.cursus.util.Messages;

public class RaceTreePopupMenu extends JPopupMenu implements ActionListener {
	private final Component main;
	private final Race race;

	private JMenuItem mnuEditRace;
	private JMenuItem mnuDeleteRace;

	private enum Commands {
		EDIT_RACE, DELETE_RACE;
	}

	public RaceTreePopupMenu(Component main, Race race) {
		this.main = main;
		this.race = race;

		mnuEditRace = new JMenuItem(Messages.getString("menu.race.edit")); //$NON-NLS-1$
		mnuEditRace.setMnemonic(KeyEvent.VK_F2);
		add(mnuEditRace);

		mnuDeleteRace = new JMenuItem(Messages.getString("menu.race.delete")); //$NON-NLS-1$
		mnuDeleteRace.setMnemonic(KeyEvent.VK_DELETE);
		add(mnuDeleteRace);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		switch (Commands.valueOf(ae.getActionCommand())) {
		case EDIT_RACE:
			break;
		case DELETE_RACE:
			break;
		}
	}
}