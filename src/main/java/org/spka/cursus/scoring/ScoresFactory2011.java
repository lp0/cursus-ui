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
package org.spka.cursus.scoring;

import eu.lp0.cursus.scoring.AveragingRacePointsData;
import eu.lp0.cursus.scoring.GenericRacePointsData;
import eu.lp0.cursus.scoring.RacePointsData;
import eu.lp0.cursus.scoring.Scores;

public class ScoresFactory2011 extends ScoresFactory2010 {
	@Override
	public RacePointsData newRacePointsData(Scores scores) {
		return new RacePointsData2011<Scores>(scores, GenericRacePointsData.FleetMethod.SERIES, AveragingRacePointsData.AveragingMethod.AFTER_DISCARDS,
				AveragingRacePointsData.Rounding.ROUND_HALF_UP);
	}
}