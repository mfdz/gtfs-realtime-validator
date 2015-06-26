/*
 ***********************************************************************************************************************
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 ***********************************************************************************************************************
*/

package edu.usf.cutr.gtfsrtvalidator.servlets;

import com.google.gson.Gson;
import edu.usf.cutr.gtfsrtvalidator.json.GtfsRtFeeds;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TriggerBackgroundServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        Enumeration<String> paramNames = request.getParameterNames();
//
//        while (paramNames.hasMoreElements()) {
//            String paramName = paramNames.nextElement();
//            System.out.println("Parameter Name " + paramName);
//
//            System.out.println("Parameter Value " + request.getParameter(paramName));
//        }
//        System.out.println(gtfsRtFeeds);

        String gtfsRtFeeds = request.getParameter("gtfsRtFeeds");
        Gson gson = new Gson();

        //Instruct GSON to parse as a Post array (which we convert into a list)
        List<GtfsRtFeeds> posts = Arrays.asList(gson.fromJson(gtfsRtFeeds, GtfsRtFeeds[].class));
        System.out.println(posts.get(1).toString());


//        BackgroundMultiThread.startBackgroundTask("http://api.bart.gov/gtfsrt/tripupdate.aspx");
//        BackgroundMultiThread.startBackgroundTask("http://api.bart.gov/gtfsrt/alerts.aspx");
//        BackgroundMultiThread.startBackgroundTask("http://api.bart.gov/gtfsrt/alerts.aspx");

    }

}