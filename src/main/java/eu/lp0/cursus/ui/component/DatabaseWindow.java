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
package eu.lp0.cursus.ui.component;

import java.awt.Frame;

import eu.lp0.cursus.db.Database;
import eu.lp0.cursus.db.data.RaceEntity;

public interface DatabaseWindow {
	public Frame getFrame();

	public Database getDatabase();

	public RaceEntity getSelected();

	public void refreshRaceList();

	public void refreshTab(final DatabaseTabSync<?> tab);

	public void reloadCurrentTabs();
}