/*
 * Copyright (C) 2011 Nipuna Gunathilake.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.usf.cutr.gtfsrtvalidator.validation.entity;

import com.google.transit.realtime.GtfsRealtime;
import edu.usf.cutr.gtfsrtvalidator.api.model.MessageLogModel;
import edu.usf.cutr.gtfsrtvalidator.api.model.OccurrenceModel;
import edu.usf.cutr.gtfsrtvalidator.helper.ErrorListHelperModel;
import edu.usf.cutr.gtfsrtvalidator.validation.ValidationRules;
import edu.usf.cutr.gtfsrtvalidator.validation.interfaces.FeedEntityValidator;
import org.hsqldb.lib.StringUtil;
import org.onebusaway.gtfs.impl.GtfsDaoImpl;
import org.onebusaway.gtfs.model.Trip;

import java.util.*;

/**
 * ID: e003
 * Description: All trip_ids provided in the GTFS-rt feed must appear in the GTFS data
 */
public class CheckTripId implements FeedEntityValidator {
    @Override
    public List<ErrorListHelperModel> validate(GtfsDaoImpl gtfsData, GtfsRealtime.FeedMessage feedMessage) {
        Collection<Trip> gtfsTripIds = gtfsData.getAllTrips();

        MessageLogModel messageLogModel = new MessageLogModel(ValidationRules.E003);
        List<OccurrenceModel> errorOccurrenceList = new ArrayList<>();

        Set<String> tripList = new HashSet<>();

        // Get a all trip_ids from the GTFS feed
        for (Trip trip : gtfsTripIds) {
            tripList.add(trip.getId().getId());
        }

        //Check the trip_id values against the values from the GTFS feed
        for (GtfsRealtime.FeedEntity entity : feedMessage.getEntityList()) {
            if (entity.hasTripUpdate()) {
                String tripId = entity.getTripUpdate().getTrip().getTripId();
                if (!tripList.contains(tripId)) {
                    OccurrenceModel occurrenceModel = new OccurrenceModel("$.entity.*.trip_update.trip[?(@.trip_id==\""+ tripId +"\")]", tripId);
                    errorOccurrenceList.add(occurrenceModel);
                }
            }
            if (entity.hasVehicle() && entity.getVehicle().hasTrip()) {
                String tripId = entity.getTripUpdate().getTrip().getTripId();
                if (!StringUtil.isEmpty(tripId) && !gtfsTripIds.contains(tripId)) {
                    OccurrenceModel occurrenceModel = new OccurrenceModel("$.entity.*.vehicle.trip[?(@.route_id==\"" + tripId + "\")]", tripId);
                    errorOccurrenceList.add(occurrenceModel);
                }
            }
        }
        return Arrays.asList(new ErrorListHelperModel(messageLogModel, errorOccurrenceList));
    }
}
