package prozori;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;




public class FileChooser{

	
//	public FileChooser() {
//		filechooser = new JFileChooser();
//		getContentPane().add(filechooser, BorderLayout.CENTER);
//		setBounds(100, 100, 600, 500);
//		setVisible(true);
//	}
	
public byte[] prebaciUBajtove(File file) throws IOException{
	Path path = Paths.get(file.getAbsolutePath());
	byte[] slika=Files.readAllBytes(path);
	return slika;
}
	public String getExtension(File f) {
	    String ext = null;
	    String s = f.getName();
	    int i = s.lastIndexOf('.');
	    if (i > 0 &&  i < s.length() - 1) {
	        ext = s.substring(i+1).toLowerCase();
	    }
	    return ext;
	}
	public boolean isImage(String extension){
		if (extension != null) {
			if (extension.equals("tiff") || extension.equals("tif") || extension.equals("gif") ||
		    extension.equals("jpeg") || extension.equals("jpg") || extension.equals("png")) {
				return true;
		    } else {
		    	return false;
		    }
		}

		return false;
	}

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		JFileChooser file=new JFileChooser();
//		file.setCurrentDirectory(new File(System.getProperty("user.home")));
//		FileNameExtensionFilter filter=new FileNameExtensionFilter("*.Images","jpg","gif","png");
//		file.addChoosableFileFilter(filter);
//		int result=file.showOpenDialog(null);
//		if(result==JFileChooser.APPROVE_OPTION){
//			//treba proveriti ekstenziju
//			File selectedFile=file.getSelectedFile();
//			String extension = getExtension(selectedFile);
//		    if(isImage(extension)){
//			try {
//				bajtovi=prebaciUBajtove(selectedFile); //ovu promenljvu hoces da setujes i onda posle kazes byte[] slika=bajtovi;
//				System.out.println(bajtovi);
//			} catch (IOException e1) {
//				JOptionPane.showMessageDialog(null,"Greska pri uploadovanju slike, molimo Vas pokusajte opet");
//				e1.printStackTrace();
//			}
//		   }
//		    else{
//		    	JOptionPane.showMessageDialog(null,"Odaberite fajl koji je slika");
//		    }
//		}
//
//		else if(result==JFileChooser.CANCEL_OPTION){
//			System.out.println("No file selected");
//			JOptionPane.showMessageDialog(null,"No file selected");
//		}
//	}
}
