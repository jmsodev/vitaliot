/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.vital.vitalcep.utils;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author a601149
 */
public class DolceInputOutput {
    
    final static Logger logger = Logger.getLogger(DolceInputOutput.class);
    
    /**
     * Transform the observations in JSONLD array into dolce inputs
     *
     * @param input the json array
     * @return  the dolce input string 
     */
    static public  ArrayList<String> speedJsonldArray2DolceInput
        (JSONArray input) {
        
        ArrayList<String> dolceInputs = new ArrayList<>();
        
        for(int i = 0; i < input.length(); i++){
            
            String dolceInput;
            
            String number = "1";

            // get sensor
            String id = input.getJSONObject(i).getString("ssn:observedBy");

            String oType = input.getJSONObject(i)
                    .getJSONObject("ssn:observationProperty")
                    .getString("type");

            String[] vect = oType.split(":");

            String observationType = vect[vect.length-1];
            //String observationType =oType; 

            JSONObject oResoult = input.getJSONObject(i)
                    .getJSONObject("ssn:observationResult");

            Double value = oResoult.getJSONObject("ssn:hasValue")
                    .getDouble("value");
            
            JSONObject oLoc = input.getJSONObject(i)
                    .getJSONObject("dul:hasLocation");

            Double glat = oLoc.getDouble("geo:lat");
            Double glong = oLoc.getDouble("geo:long");
            

            dolceInput = number +" "+observationType
                +" pos location "+glong.toString()+"\\"+glat.toString()
                +" string id "+id
                +" float value "+value.toString()+"";
               // +" int value "+(long)Math.floor(value + 0.5d);
            dolceInputs.add(dolceInput);
            
        }
        
        return dolceInputs;
      
    }

    /**
     * Transform the dolce output into vital observations in jsonld 
     *
     * @param input the json 
     * @param id 
     * @param sensor 
     * @param observationTime ***see whether to put it
     * @return  the dolce input string 
     */
    static public JSONObject dolceOutput2Jsonld(String input,String id, 
            String sensor, String observationTime) {

            JSONObject outputObservation = new JSONObject();
            
            String[] values = input.split(" ");
            
            String valueEvent= null;
            String locationEvent= null;
            String idEvent= null;
            
            for (int z=3;z<values.length;z++){
                String token = values[z];
                if (token.compareToIgnoreCase("id")==0)
                   idEvent=values[z+1];
                else if (token.compareToIgnoreCase("location")==0)
                    locationEvent = values [z+1];
                else if (token.compareToIgnoreCase("value")==0)
                    valueEvent = values [z+1];
            }           
            
            outputObservation.put("@context",
                    "http://vital-iot.eu/contexts/measurement.jsonld");
            
            outputObservation.put("id",sensor+"/observation/"+id );
            
            outputObservation.put("type","ssn:Observation");
            
            outputObservation.put("ssn:observedBy",sensor);
            
            JSONObject property = new JSONObject();
            property.put("type","vital:ComplexEvent");
            outputObservation.put("ssn:observationProperty",property);
            
            JSONObject resultTime = new JSONObject();
            resultTime.put("time:inXSDDateTime",observationTime);//check format
            outputObservation.put("ssn:observationResultTime",resultTime);
                //"time:inXSDDateTime": "2015-10-14T11:59:11+02:00"
            
            JSONObject hasValue = new JSONObject();
            hasValue.put( "type","ssn:ObservationValue");
            
            JSONObject valuex = new JSONObject();
            JSONObject value = new JSONObject();
            value.put("complexEvent",values[1]);
            
            
            /* busca localization*/
            String[] aLoc = locationEvent.split("\\\\");  //de Elisa values[10].split("\\");
            
            JSONObject loc = new JSONObject();
            loc.put("type","geo:Point");
            loc.put("geo:lat",aLoc[1]);//ver
            loc.put("geo:long",aLoc[0]);//ver
            //value.put("dul:hasLocation",loc);
            
            /* busca el value*/
            JSONObject speedObs = new JSONObject();
            speedObs.put("type","ssn:SensorOutput");
            JSONObject speedValue = new JSONObject();
            speedValue.put("type","ssn:ObservationValue");
            speedValue.put("value",valueEvent);   //de Elisavalues[5]);
            speedValue.put("qudt:unit","qudt:KilometerPerHour");
            
            speedObs.put("ssn:hasValue",speedValue);
           
            //value.put("ssn:ObservationResult",speedObs);
            //ver si falta algo
            
            value.put("ssn:observedBy",idEvent);
            
            valuex.put("value",value.toString());
            valuex.put("type","ssn:ObservationValue");

            JSONObject observationResult = new JSONObject();
            observationResult.put("ssn:hasValue",valuex);
            observationResult.put("type","ssn:SensorOutput");
            outputObservation.put("ssn:observationResult",observationResult);
           
        return outputObservation;
           
    }
 
    /**
     * Transform an observation in JSONLD into a dolce input.
     *
     * @param input the json array
     * @return  the dolce input string 
     */
    static public  String speedJsonldDolceInput
        (JSONObject input) {
        
        
            
            String dolceInput;
            
            String number = "1";

            // get sensor
            String id = input.getString("ssn:observedBy");

            String oType = input.getJSONObject("ssn:observationProperty")
                    .getString("type");

            String[] vect = oType.split("/");

            String observationType = vect[vect.length-1];

            JSONObject oResoult = input.getJSONObject("ssn:observationResult");

            String value = oResoult.getJSONObject("ssn:hasValue")
                    .getString("value");
            
            JSONObject oLoc = input.getJSONObject("dul:hasLocation");

            String glat = oLoc.getString("geo:lat");
            String glong = oLoc.getString("geo:long");
            

            dolceInput = number +" "+observationType
                +" pos "+glong+"\\"+glat    
                +" String "+id
                +" float "+value;
            
        
        return dolceInput;
      
    }
       
}
