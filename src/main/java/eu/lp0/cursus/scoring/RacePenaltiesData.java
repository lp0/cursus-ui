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
package eu.lp0.cursus.scoring;

import java.util.Map;

import com.google.common.collect.Table;

import eu.lp0.cursus.db.data.Pilot;
import eu.lp0.cursus.db.data.Race;

public interface RacePenaltiesData {
	public Table<Pilot, Race, Integer> getRacePenalties();

	public Map<Race, Integer> getRacePenalties(Pilot pilot);

	public Map<Pilot, Integer> getRacePenalties(Race race);

	public Integer getRacePenalties(Pilot pilot, Race race);
}