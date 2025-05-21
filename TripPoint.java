

	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.IOException;
	import java.util.ArrayList;
public class TripPoint {
   
		private int time;
	    private double lat;
	    private double lon;
	     private static ArrayList<TripPoint> trip = new ArrayList<>();

	    public TripPoint(int time, double lat, double lon) {
	        this.time = time;
	        this.lat = lat;
	        this.lon = lon;
	    }

	    public int getTime() {
	        return time;
	    }

	    public double getLat() {
	        return lat;
	    }

	    public double getLon() {
	        return lon;
	    }

	    public static ArrayList<TripPoint> getTrip() {
	        return new ArrayList<>(trip);
	    }

	    public static void readFile(String filename) throws IOException {
	    	trip.clear();
	        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        	
	        	br.readLine();
	  
	            String line;
	            while ((line = br.readLine()) != null) {
	            	String[] data = line.split(",");
	                trip.add(new TripPoint(Integer.parseInt(data[0]), Double.parseDouble(data[1]), Double.parseDouble(data[2])));
	            }
	        }
	        
	    }

	    public static double haversineDistance(TripPoint a, TripPoint b) {
	        double earthRadius = 6371; // in kilometers
	        double lat1 = Math.toRadians(a.getLat());
	        double lon1 = Math.toRadians(a.getLon());
	        double lat2 = Math.toRadians(b.getLat());
	        double lon2 = Math.toRadians(b.getLon());

	        double dLat = lat2 - lat1;
	        double dLon = lon2 - lon1;

	        double hav = Math.pow(Math.sin(dLat / 2), 2) +
	                     Math.cos(lat1) * Math.cos(lat2) *
	                     Math.pow(Math.sin(dLon / 2), 2);

	        double distance = 2 * earthRadius * Math.asin(Math.sqrt(hav));
	        return distance;
	    }
	   

	    public static double totalDistance() {
	        double total = 0;
	        for (int i = 0; i < trip.size() - 1; i++) {
	            total += haversineDistance(trip.get(i), trip.get(i + 1));
	        }
	        return total;
	    }
	    public static double totalTime() throws IOException {
	    	return trip.isEmpty() ? 0 : (trip.get(trip.size() - 1).getTime() - trip.get(0).getTime()) / 60.0;
	    }


	    public static double avgSpeed(TripPoint a, TripPoint b) {
	        double distance = haversineDistance(a, b);
	        double time = Math.abs(a.getTime() - b.getTime()) / 60.0; // convert minutes to hours
	        return distance / time;
	    }
	}


