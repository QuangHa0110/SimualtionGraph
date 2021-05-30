package Final;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
public class DataInput {
	private ArrayList<ArrayList<Integer>> danhSachDinh= new ArrayList<ArrayList<Integer>>();
	private String path;
	private File file;
	private int maxDinh=0;
	
	
	public DataInput(String path) {
		super();
		this.path = path;
		file = new File(path);
		initValue();
		readInput();
	}

	public void initValue() {
		try {
			
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line ="";
			while((line= bufferedReader.readLine())!=null) {
				String [] s = line.split(" ");
				for(String a: s) {
					if(maxDinh < Integer.parseInt(a)) {
						maxDinh = Integer.parseInt(a);
					}
					
				}
				
			}
			
			for(int i=0;i<=maxDinh;i++) {
				ArrayList<Integer> list = new ArrayList<>();
				danhSachDinh.add(list);
				
				
			}
			fileReader.close();
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}
	public void readInput() {
		
			FileReader fileReader;
			try {
				fileReader = new FileReader(file);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line ="";
				while((line = bufferedReader.readLine())!=null) {
					String [] s = line.split(" ");
					int a = Integer.parseInt(s[0]);
					for(int i=1;i<s.length;i++) {
						danhSachDinh.get(a).add(Integer.parseInt(s[i]));
					}
					
				}
				bufferedReader.close();
				fileReader.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
	}
	public int getMaxDinh() {
		return maxDinh;
	}
	public void setMaxDinh(int maxDinh) {
		this.maxDinh = maxDinh;
	}
	public ArrayList<ArrayList<Integer>> getDanhSachDinh() {
		return danhSachDinh;
	}
	public void setDanhSachDinh(ArrayList<ArrayList<Integer>> danhSachDinh) {
		this.danhSachDinh = danhSachDinh;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}

	
	
	
	
	
	
	
	
	
    	
    	
}
