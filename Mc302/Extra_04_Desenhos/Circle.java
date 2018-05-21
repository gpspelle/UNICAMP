class Circle { 

    public static void main(String[] args) { 
        DrawMeACircle(10, 10, 10); // should print to the screen an ellipse looking circle 
    }

    public static void DrawMeACircle(double posX, double posY, double radius) { 

        double xaxis = 50;  // scanning the coordinates
        double yaxis = 30;  // " " 

        for (double x = 0; x < xaxis; x++) { 
            for (double y = 0; y < yaxis; y++) { 

                //using the formula for a cicrle
                double a = Math.abs((posX - x) * (posX - x)); 
                double b = Math.abs((posY - y) * (posY - y)); 

                double c = Math.abs(a + b);        
                double d = Math.abs(radius * radius); 

                // checking if the formula stands correct at each coordinate scanned
                int arbitraryNumber = (int)radius;
                if ( Math.abs(c - d) < arbitraryNumber) {
                     System.out.print("*"); 
                }                
                //if ( c == d) {
                    //System.out.print('#'); 
                //} 
                else { 
                    System.out.print(' '); 
                }
            }  
            System.out.println();
        } 
    }
}
