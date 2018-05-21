import java.io.*;

public class Main {

  public static void main (String[] args) throws IOException {
    try {
      test();
    } catch (IOException e) {
      System.out.println("Caguei pra voce");
    }

  }
  private void test1() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("C:/ empty.txt"));
   // System.out.println(reader.getLine());
  }
  static void test2() throws IOException {
    FileWriter writer = new  FileWriter("fun.log");
    writer.write("Hello!");
    writer.close ();
  }
  static void  test()  throws  IOException {
    for (int  index = 1;  index  <= 2;  index ++) {
      PrintWriter writer = new  PrintWriter("apa");
      writer.print("apa");
      writer.close ();
    }
  }
}
