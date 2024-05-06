package unclassified;

public class Temp {

    public static void main(String[] args) {
        int placeHolder=0;
        String text="143,136";

        for(int i=0;i<=text.length();i++) {
            if(text.charAt(i)==',' || i==text.length()-1)
            {
                System.out.println(text.substring(placeHolder,i));
                placeHolder=i+1;
            }
        }
    }
}
