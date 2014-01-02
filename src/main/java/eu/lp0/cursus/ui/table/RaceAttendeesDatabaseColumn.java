/*
	cursus - Race series management program
	Copyright 2011-2012, 2014  Simon Arlott

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
package eu.lp0.cursus.ui.table;

import java.util.Collections;

import javax.swing.JComboBox;

import com.google.common.collect.Collections2;
import com.google.common.collect.Ordering;

import eu.lp0.cursus.db.dao.EntityDAO;
import eu.lp0.cursus.db.data.Race;
import eu.lp0.cursus.ui.component.DatabaseColumn;
import eu.lp0.cursus.ui.component.DatabaseTableCellEditor;
import eu.lp0.cursus.ui.component.DatabaseWindow;
import eu.lp0.cursus.ui.component.MutableListComboBoxModel;
import eu.lp0.cursus.ui.component.StringDatabaseTableCellRenderer;
import eu.lp0.cursus.util.PilotRaceNumberComparator;

public class RaceAttendeesDatabaseColumn extends DatabaseColumn<RaceAttendeePenalty, PilotWrapper> {
	private final MutableListComboBoxModel<PilotWrapper> pilots = new MutableListComboBoxModel<PilotWrapper>();

	public RaceAttendeesDatabaseColumn(String name) {
		super(name);
		cellRenderer = new StringDatabaseTableCellRenderer<RaceAttendeePenalty, PilotWrapper>(this);
	}

	public RaceAttendeesDatabaseColumn(String name, DatabaseWindow win, EntityDAO<RaceAttendeePenalty> dao) {
		super(name, win, dao);
		cellRenderer = new StringDatabaseTableCellRenderer<RaceAttendeePenalty, PilotWrapper>(this);
		cellEditor = new DatabaseTableCellEditor<RaceAttendeePenalty, PilotWrapper>(this, new JComboBox(pilots));
	}

	@Override
	protected PilotWrapper getValue(RaceAttendeePenalty row, boolean editing) {
		return new PilotWrapper(row.getPilot());
	}

	@Override
	protected boolean setValue(RaceAttendeePenalty row, PilotWrapper value) {
		if (value == null) {
			row.setPilot(null);
		} else {
			row.setPilot(value.getPilot());
		}
		return true;
	}

	public void setRace(Race race) {
		if (race != null) {
			pilots.replaceAll(Collections2.transform(Ordering.from(new PilotRaceNumberComparator()).sortedCopy(race.getAttendees().keySet()),
					PilotWrapper.getFunction()));
		} else {
			pilots.replaceAll(Collections.<PilotWrapper>emptySet());
		}
	}
}