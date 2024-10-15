package convert;

import java.util.Scanner;

public class UnitsConvertor {

// No instances please.  All methods are static.
private UnitsConvertor() {
}

    public static void main(String[] args) {
        // parses user input, checking both integer and string 
    	System.out.println("Please Enter the input value followed by the unit:");
        Scanner reader = new Scanner(System.in);
        
        if(reader.hasNextLong()) {
            long number = reader.nextLong();
            if(reader.hasNext("\\b+(mil|in|inch|ft|foot|feet|yd|yard|mi|mile)\\b+")) {
                String unit = reader.findInLine("\\b+(mil|in|inch|ft|foot|feet|yd|yard|mi|mile)\\b+");
                double mm = toMm(number, unit);
                System.out.println(number + " " + unit + " is:");
                System.out.println(String.format("%f", mm) + " mm");
                System.out.println(String.format("%f", mm/10) + " cm" );
                System.out.println(String.format("%f", mm/1000) + " m");
                System.out.println(String.format("%f", mm/1000000) + " km");
            }
            else if(reader.hasNext("\\b+(mm|millimeter|cm|centimeter|m|meter|km|kilometer)\\b+")) {
                String unit = reader.findInLine("\\b+(mm|millimeter|cm|centimeter|m|meter|km|kilometer)\\b+");
                double mil = toMil(number, unit);
                System.out.println(number + " " + unit + " is:");
                System.out.println(String.format("%.2g", mil) + " mil");
                System.out.println(String.format("%.2g", mil/1000) + " inch");
                System.out.println(String.format("%.2g", mil/12000) + " ft");
                System.out.println(String.format("%.2g", mil/36000) + " yard");
                System.out.println(String.format("%.2g", mil/63360000) + " mile");
            }
            else {
                System.out.println("Invalid input");
            }
        }
        else {
            System.out.println("Invalid input");
        }
        reader.close();
        return;
    } 
    
    // convert any metric system with unit specified in second parameter to mil
    public static double toMil(long metric, String unit) {
        double mm;
        
        if(unit.matches("\\b+(mm|millimeter)\\b+")) {
        	mm = metric;
        } else if (unit.matches("\\b+(cm|centimeter)\\b+")) {
            mm = metric*10;
        } else if(unit.matches("\\b+(m|meter)\\b+")) {
            mm = metric*1000;
        } else if(unit.matches("\\b+(km|kilometer)\\b+")) {
            mm = metric*1000000;
        } else {
        	throw new IllegalArgumentException("Bad unit value");
        }
         
        return mm * 39.3701;
    }
    
    // convert any imperial system with unit specified in second parameter to mm
    public static double toMm(long imperial, String unit) {
        double mil;
        
        if(unit.matches("\\b+(in|inch)\\b+")) {
            mil = imperial*1000;
        }
       else if(unit.matches("\\b+(ft|foot|feet)\\b+")) {
            mil = imperial*12000;
        }
       else if(unit.matches("\\b+(yd|yard)\\b+")) {
            mil = imperial*36000;
        }
       else if(unit.matches("\\b+(mi|mile)\\b+")) {
            mil = imperial*63360000;
        }
        else if(unit.matches("\\b+mil\\b+")){
            mil = imperial;
        } else {
        	throw new IllegalArgumentException("Illegal unit value.");
        }
         
        return mil * 0.0254;
    }
}
