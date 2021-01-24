package Angel;


import java.io.*;
import java.util.Random;
import java.lang.Character;

public class bibleReader {
    Random random = new Random();
    int lineCount = 99818;
    String passage = "";
    String nextLine = "";
    String bookName;
    InputStream fs;
    BufferedReader br;
    int bookNum = 1;
    int readFrom = 0;
    String tmpPassage = "";
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    public boolean startRead() {
        try {
            nextLine = "";
            fs = classloader.getResourceAsStream("Angel/bibleTxt/bible-"+bookNum+".txt");
            if (fs != null){
                br = new BufferedReader(new InputStreamReader(fs));
                String tmp = br.readLine();
                readFrom ++;
                bookName = tmp.split("##")[0];
                lineCount = Integer.parseInt(tmp.split("##")[1]);
                for (int i = 1; i < readFrom; i++){
                    br.readLine();
                }
                return true;
            } else {
                System.out.println("File is null");
            }
        } catch (IOException e) {
            System.out.println("bibleReader::startRead::ReadFailed");
        }
        return false;
    }
    public void readNext(){
        passage = "";
        try {
            while (passage.isEmpty()){
                //System.out.println(nextLine + ":" + readFrom);
                if(nextLine.trim().length() > 0){
                    char[] lineChar;
                    lineChar = nextLine.toCharArray();
                    //System.out.println(nextLine);
                    if(Character.isDigit(lineChar[0]) && tmpPassage.isEmpty()){
                        //System.out.println("set tmp as: " + nextLine);
                        tmpPassage = nextLine;
                    } else if (!tmpPassage.isEmpty() && !Character.isDigit(lineChar[0])){
                        //System.out.println("added to tmp: " + nextLine);
                        tmpPassage += " " + nextLine;
                    } else if (Character.isDigit(nextLine.toCharArray()[0]) && !tmpPassage.isEmpty()){
                        passage = tmpPassage;
                        tmpPassage = "";
                    }
                }
                if (passage.isEmpty()){
                    if(readFrom == lineCount){
                        bookNum ++;
                        readFrom = 0;
                        startRead();
                        readNext();
                    }
                    nextLine = br.readLine();
                    readFrom ++;
                }
            }
        } catch (IOException e) {
            System.out.println("bibleReader::readNext::ReadFailed");
        }
        //System.out.println(passage);
    }
    public void randRead(){
        readFrom = 0;
        bookNum = random.nextInt(66);
        startRead();
        readFrom = random.nextInt(lineCount);
        startRead();
        System.out.println("Title: " + bookName);
        readNext();
    }
    public void read(){
        if(br == null){
            startRead();
        }
        System.out.println("Title: " + bookName);
        readNext();
    }
    public void nextBook(){
        readFrom = 0;
        System.out.println("Title: " + bookName);
        System.out.println("Book num: " + bookNum);
        if (bookNum == 66){
            bookNum = 1;
        } else {
            bookNum ++;
        }
        startRead();
        readNext();
    }
    public void prevBook(){
        readFrom = 0;
        if (bookNum == 1){
            bookNum = 66;
        } else {
            bookNum --;
        }
        startRead();
        readNext();
    }
}
