/*
	cursus - Race series management program
	Copyright 2012  Simon Arlott

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
package eu.lp0.cursus.scoring.scores.impl;

import java.util.List;
import java.util.Set;

import com.google.common.base.Predicate;

import eu.lp0.cursus.db.data.Event;
import eu.lp0.cursus.db.data.Pilot;
import eu.lp0.cursus.db.data.Race;
import eu.lp0.cursus.scoring.data.OverallPenaltiesData;
import eu.lp0.cursus.scoring.data.OverallPointsData;
import eu.lp0.cursus.scoring.data.OverallPositionData;
import eu.lp0.cursus.scoring.data.RaceDiscardsData;
import eu.lp0.cursus.scoring.data.RaceLapsData;
import eu.lp0.cursus.scoring.data.RacePenaltiesData;
import eu.lp0.cursus.scoring.data.RacePointsData;
import eu.lp0.cursus.scoring.data.RacePositionsData;
import eu.lp0.cursus.scoring.data.ScoresFactory;
import eu.lp0.cursus.scoring.scorer.Scorer;
import eu.lp0.cursus.scoring.scores.base.AbstractScores;

public class GenericScores extends AbstractScores {
	protected final RaceLapsData raceLapsData;
	protected final RacePointsData racePointsData;
	protected final RacePenaltiesData racePenaltiesData;
	protected final RacePositionsData racePositionsData;
	protected final RaceDiscardsData raceDiscardsData;

	protected final OverallPenaltiesData overallPenaltiesData;
	protected final OverallPointsData overallPointsData;
	protected final OverallPositionData overallPositionData;

	public GenericScores(Set<Pilot> pilots, List<Race> races, Set<Event> events, Predicate<Pilot> fleetFilter, ScoresFactory scoresFactory, Scorer scorer) {
		super(pilots, races, events, fleetFilter, scoresFactory, scorer);

		raceLapsData = scoresFactory.newRaceLapsData(this);
		racePointsData = scoresFactory.newRacePointsData(this);
		racePenaltiesData = scoresFactory.newRacePenaltiesData(this);
		racePositionsData = scoresFactory.newRacePositionsData(this);
		raceDiscardsData = scoresFactory.newRaceDiscardsData(this);

		overallPenaltiesData = scoresFactory.newOverallPenaltiesData(this);
		overallPointsData = scoresFactory.newOverallPointsData(this);
		overallPositionData = scoresFactory.newOverallPositionData(this);
	}

	// ForwardingScores
	@Override
	protected RaceLapsData delegateRaceLapsData() {
		return raceLapsData;
	}

	@Override
	protected RacePointsData delegateRacePointsData() {
		return racePointsData;
	}

	@Override
	protected RacePenaltiesData delegateRacePenaltiesData() {
		return racePenaltiesData;
	}

	@Override
	protected RacePositionsData delegateRacePositionsData() {
		return racePositionsData;
	}

	@Override
	protected RaceDiscardsData delegateRaceDiscardsData() {
		return raceDiscardsData;
	}

	@Override
	protected OverallPenaltiesData delegateOverallPenaltiesData() {
		return overallPenaltiesData;
	}

	@Override
	protected OverallPointsData delegateOverallPointsData() {
		return overallPointsData;
	}

	@Override
	protected OverallPositionData delegateOverallPositionData() {
		return overallPositionData;
	}
}