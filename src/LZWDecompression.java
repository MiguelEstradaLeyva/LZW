/**
 * Miguel Estrada
 * Decompress file
 * Data structures
 */
import java.util.*;
import java.io.*;
public class LZWDecompression {

    public static void main (String []args)throws Exception{
        //basic main to get info from user and call methods
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the compressed file name with extension .lzw ");
        String file = scan.nextLine();
        System.out.println("Please enter the name of the decompress file you want it to be fallowed by a dot(.)");
        String decompressfile = scan.nextLine();
        InputFile = decompressfile;
        decompress(file);
    }
/**
 * Decompress method takes in a file, we read the file
 * that I created and try to do the opposite from when I compressed
 * it. and put it in a decoded.txt file
 */
    public static void decompress(String inputfile) throws Exception{
        //used same logic from compress method
        int dictsize = 128;
        Map<Integer, String>  dic = new HashMap<Integer,String>();
        for(int i = 0; i < 128; i++) {
            dic.put(i, "" + (char) i);
        }
        List<Integer> compValues = new ArrayList<Integer>();
        double maxTabSize = 256;
        //scan file in put in array again
        RandomAccessFile fin = new RandomAccessFile(new File(inputfile), "r");
        int b = fin.read();
        while(b!= -1){
            compValues.add(b);
            b = fin.read();
        }
        //compare and decompress characters
        String encodValues = ""+(char)(int)compValues.remove(0);
        StringBuffer decodValues = new StringBuffer(encodValues);
        String tabValue = null;
        //compare with dic and decompress file
        for(int key: compValues){
            if(dic.containsKey(key)){
                tabValue = dic.get(key);
            }else if(key == dictsize){
                tabValue = encodValues+encodValues.charAt(0);
            }
            decodValues.append(tabValue);
            if(dictsize< maxTabSize) {
                dic.put(dictsize++, encodValues + tabValue.charAt(0));
            }
            encodValues = tabValue;
        }
        CreateFile(decodValues.toString());
    }
    public static String InputFile= null;
    public static String LZW_filename;
    //this method is used to output the file in a _decoded.txt output
    //since i put in a .LZW extension
    public static void CreateFile(String decodedValues)throws Exception{
        LZW_filename = InputFile.substring(0, InputFile.indexOf(".")) + "_decoded.txt";

        FileWriter writer = new FileWriter(LZW_filename, true);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        try {
            bufferedWriter.write(decodedValues);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedWriter.flush();

        bufferedWriter.close();
    }
}
