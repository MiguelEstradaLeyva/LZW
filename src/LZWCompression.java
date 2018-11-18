/**
 * Miguel Estrada
 * LZW compression program(compressed)
 * Data Structures
 */
import java.io.*;
import java.io.RandomAccessFile;
import java.util.*;
import java.lang.String;
public class LZWCompression {
    public static void main(String[]args) throws Exception{
        //basic stuff acquiring from user
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the name of the file to be compress, with the file extension ");
        String TheFile = input.nextLine();
        System.out.println("Please enter the name you want the compress file to be followed by a dot(.) ");
        String compressfile = input.nextLine();
        InputFile = compressfile;
        Compress(TheFile);
        System.out.println("The file will be compressed with the .lzw extension.");

    }
    /**
     * In this compress method I pass in the file, to be compress.
     * @param file
     * @throws Exception
     */
    public static void Compress(String file) throws Exception {
        int dicSize = 128;
        //create map
        Map<String, Integer> table = new HashMap<String, Integer>();
        for (int i = 0; i < 128; i++) {
            table.put("" + (char) i, i);
        }
        //scan the file in.
        RandomAccessFile fin = new RandomAccessFile(new File(file), "r");
        int b = fin.read();
        //create array
        List<Character> theList = new ArrayList<>();
        //scan everything in file add to array.
        while(b!= -1){
            theList.add((char) b);
            b = fin.read();
                }

            //create array to hold compressed ascii numbers
            List<Integer> encodValues = new ArrayList<Integer>();
            //System.out.println(theList);
            String initString = "";
            //compute LZW compression as we are comparing it with out map
            for(char c :theList) {
            String wc = initString + c;

            if (table.containsKey(wc))
            initString = wc;
            else{
            encodValues.add(table.get(initString));
            table.put(wc, dicSize++);
            initString = "" + c;
            }
            }
            if (!initString.equals(""))
                encodValues.add(table.get(initString));
            System.out.println("This are the compressed values " + encodValues);
            //create the file with the encoded values(array)
            CompressLZWFile(encodValues);
        }
    private static String LZW_filename;
    private static String InputFile= null;
    //in this method I pass in the array of encoded values
    //I create the output file with the .lzw extension
    public static void CompressLZWFile( List<Integer> encodedValues) throws IOException{
        BufferedWriter out = null;
        LZW_filename = InputFile.substring(0,InputFile.indexOf(".")) + ".lzw";

        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(LZW_filename),"UTF_16BE")); //16-bit compressed file

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Iterator<Integer> Itr = encodedValues.iterator();
            while (Itr.hasNext()) {
                out.write(Itr.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }
}