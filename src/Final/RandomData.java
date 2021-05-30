package Final;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Random;
public class RandomData {
	private Random random = new Random();
	public static int indexFile = 1;

	public void randomData(int maxPoint) {
		String currentPathString = System.getProperty("user.dir"); // lấy đường dẫn hiện tại của hệ thống
		String filename = "data" + indexFile + ".txt";
		File file = new File(currentPathString + "\\" + filename);
		indexFile++;
	
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
			int a = 0;
			for (int i = 1; i <= maxPoint; i++) {
				outputStreamWriter.write(i + " ");
				int rd = random.nextInt(maxPoint);
				for (int j = 0; j < rd; j++) {
					a = random.nextInt(maxPoint) + 1;
				
					outputStreamWriter.write(a + " ");
					

				}
				outputStreamWriter.write("\n");

			}
			outputStreamWriter.close();
			outputStream.close();
			

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		RandomData randomData = new RandomData();
		randomData.randomData(15);
	}

}
