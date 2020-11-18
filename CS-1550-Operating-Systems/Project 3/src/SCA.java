import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;

class SCA {

    private static int diskWrites;
    private final String filepath;
    private final int pageSize;
    private final int[] memorySplit;
    private int memoryAccesses;
    private int pageFaults;

    protected SCA(String filepath, int pageSize, int[] memorySplit) {
        this.filepath = filepath;
        this.pageSize = pageSize;
        this.memorySplit = memorySplit;
    }

    //runs second chance algorithm simulation
    protected void runSimulation() {

        //Frames allocated to process 0
        LinkedList<Page> splitOne = new LinkedList<>();
        for(int i = 0; i < memorySplit[0]; i++)
            splitOne.add(new Page());

        //Frames allocated to process 1
        LinkedList<Page> splitTwo = new LinkedList<>();
        for(int i = 0; i < memorySplit[1]; i++)
            splitTwo.add(new Page());

        int newFrameIndex = 0;
        int newFrameIndex1 = 0;

        try {
            File input = new File(filepath);
            Scanner reader = new Scanner(input);

            //read in lines from trace
            while(reader.hasNextLine()) {
                String[] data = reader.nextLine().split(" ");

                //either "s" for store or "l" for load
                String access = data[0];

                //reads in address, converts to binary and selects address based on page size
                String address = data[1].replace("0x", "");
                address = hexToBinary(address);
                int size = 32 - log(pageSize);
                String pageAddress = address.substring(0,size);

                //either 0 for process 0 or 1 for process 1
                int process = Integer.parseInt(data[2]);

                if(process == 0) {
                    //checks if address exists, if not then inserted if it is then eviction takes place
                    if(notAddressExists(memorySplit[0],splitOne,pageAddress, access)) {
                        newFrameIndex = replaceAddress(memorySplit[0],splitOne, newFrameIndex,pageAddress, access);
                        pageFaults++;
                    }
                    memoryAccesses++;
                } else {
                    //checks if address exists, if not then inserted if it is then eviction takes place
                    if(notAddressExists(memorySplit[1], splitTwo, pageAddress, access)) {
                        newFrameIndex1 = replaceAddress(memorySplit[1], splitTwo, newFrameIndex1, pageAddress, access);
                        pageFaults++;
                    }
                    memoryAccesses++;
                }
            }
            reader.close();
        } catch(FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //returns true if address does not exist, false if it does
    private static boolean notAddressExists(int numFrames, LinkedList<Page> pages, String address, String access) {
        int i = 0;
        while(i < numFrames) {
            //if there is a hit then reference bit is updated
            if(pages.get(i).getPageAddress().equals(address)) {
                pages.get(i).setReferenceBit(1);

                //if dirty bit has not been set then check if it needs to be updated
                if(!pages.get(i).getDirtyBit())
                    pages.get(i).setDirtyBit(access.equals("s"));

                return false;
            }
            i++;
        }
        return true;
    }

    //locates first available frame where reference bit is 0 and replaces it
    private static int replaceAddress(int numFrames, LinkedList<Page> pages, int i, String address, String access) {
        for(;;) {
            //finds first frame where reference bit is 0
            if(pages.get(i).getReferenceBit() == 0) {

                //replaces current frame
                pages.get(i).setPageAddress(address);

                //if dirty bit is present then it is written to disk
                if(pages.get(i).getDirtyBit())
                    diskWrites++;

                //replaces current dirty bit
                pages.get(i).setDirtyBit(access.equals("s"));

                //advance pointer to next frame
                return (i+1)%numFrames;
            }

            //continue iterating over frames until reference bit of 0 is found
            pages.get(i).setReferenceBit(0);
            i = (i+1)%numFrames;
        }
    }

    //converts hex to binary and adds leading zeros to make 32bits
    private static String hexToBinary(String hex) {
        StringBuilder stringBuilder = new StringBuilder(new BigInteger(hex, 16).toString(2));
        while(stringBuilder.length() != 32)
            stringBuilder.insert(0,"0");
        return stringBuilder.toString();
    }

    //calculates log base 2 of number
    private static int log(int number) {
        return (int) (Math.log(number)/Math.log(2));
    }

    @Override
    public String toString() {
        return "Algorithm: Second Chance\n" +
                "Number of frames: " + (memorySplit[0] + memorySplit[1]) + "\n" +
                "Page size: " + (pageSize/1024) + " KB\n" +
                "Total memory accesses: " + memoryAccesses + "\n" +
                "Total page faults: " + pageFaults + "\n" +
                "Total writes to disk: " + diskWrites;
    }
}
