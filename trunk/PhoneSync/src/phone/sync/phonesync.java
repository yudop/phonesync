package phone.sync;

import org.openintents.intents.FileManagerIntents;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RadioButton;
import android.widget.Toast;
import android.database.Cursor;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.io.File;

public class phonesync extends Activity {
	protected static final int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 1;
	
	protected EditText TxFileName;
	
	void TextLog(String LogText, int Action) {
		final TextView TxtLog = (TextView) findViewById(R.id.TextLog );
		
		switch (Action) {
			case 0: 
					TxtLog.setText(LogText);
					break;
			case 1: 
					TxtLog.setText(TxtLog.getText() + "\n" + LogText);
					break;
			case 2: 
					TxtLog.setText(TxtLog.getText() + LogText);
					break;
			
		}
			
	}
	
	public void DeleteContact()
	{
	/*
	
	 * private void removeContact(Context context, String phone) {
    	//context.getContentResolver().delete(Contacts.Phones.CONTENT_URI, phone, null);
    		context.getContentResolver().delete(Contacts.Phones.CONTENT_URI,
          	Contacts.PhonesColumns.NUMBER+"=?", new String[] {phone});
		}
	
	 */
	}
	
	public void UpdateContact()
	{
		
	}
	
	int AddContact(String stContact)
	{
			/*
	        * ADD new Contact to Android
	        * 
	        * Input: 
	        *        stContact - Format in .CSV thunderbird
	        *        
	        	0  Nome,
	        	1  Cognome,
	        	2  Nome visualizzato,
	        	3  Soprannome,
	        	4  Email primaria,
	        	5  Email secondaria,
	        	6  Telefono ufficio,
	        	7  Telefono casa,
	        	8  Numero fax,
	        	9  Numero cercapersone,
	        	10 Numero cellulare,
	        	11 Indirizzo di casa,
	        	12 Indirizzo di casa 2,
	        	13 Citt� di residenza,
	        	14 Provincia di residenza,
	        	15 CAP di residenza,
	        	16 Nazione di residenza,
	        	17 Indirizzo di lavoro,
	        	18 Indirizzo di lavoro 2,
	        	19 Citt� di lavoro,
	        	20 Provincia di lavoro,
	        	21 CAP di lavoro,Nazione di lavoro,
	        	22 Qualifica,Dipartimento,
	        	23 Organizzazione,
	        	24 Pagina web 1,
	        	25 Pagina web 2,
	        	26 Anno di nascita,
	        	27 Mese di nascita,
	        	28 Giorno di nascita,
	        	29 Personalizzato  1,
	        	30 Personalizzato  2,
	        	31 Personalizzato  3,
	        	32 Personalizzato 4,
	        	33 Note,
	        */
	
			/*
			 * Return:
			 * 		 0	: Contact is present not import
			 * 		 1	: Import contact
			 * 		-1  : Error 
			 */	
		
			String [] StCampiRubrica = null;
			StCampiRubrica = stContact.split(",");
	        ContentValues personValues = new ContentValues();
	        String Address = null;
	        
	        // Find exist a contact to Android Phone
	        String[] projection = new String[] {
	        		Contacts.People._ID,
                    Contacts.People.NAME
                 };

	        //Get the base URI for the People table in the Contacts content provider.
			Uri contacts =  Contacts.People.CONTENT_URI;
			
			String NameSt = StCampiRubrica[2];
			NameSt = NameSt.replaceAll("'", "''");
			
			//Make the query. 
			Cursor managedCursor = managedQuery(contacts,
			                projection, 												// Which columns to return 
			                Contacts.People.NAME + " = '" + NameSt  + "'",    // Which rows to return (all rows)
			                null,       												// Selection arguments (none)
			                null);
			
			
			if (managedCursor.getCount() > 0) {
				return 0;
			} 
	        	        
	        // Add Contact
	        // Nome e Cognome
	        personValues.put(Contacts.People.NAME, StCampiRubrica[2]);
	        	        
	        /* STARRED 0 = Contacts, 1 = Favorites */
	        personValues.put(Contacts.People.STARRED, 1);
	                
	        Uri newPersonUri = Contacts.People.createPersonInMyContactsGroup (getContentResolver(), personValues);
	              
	        if (newPersonUri != null) {
	        	// add email
	        	if (StCampiRubrica.length >= 5)
		        	if (StCampiRubrica[4].trim().length() != 0)
		        	{
			        	ContentValues emailValues = new ContentValues();
			        	Uri emailUri = Uri
			        			.withAppendedPath(
			        					newPersonUri,
			        					Contacts.People.ContactMethods
			        	                                       .CONTENT_DIRECTORY);
			        	emailValues.put(Contacts.ContactMethods.KIND,
			        			Contacts.KIND_EMAIL);
			        	emailValues.put(Contacts.ContactMethods.TYPE,
			        			Contacts.ContactMethods.TYPE_HOME);
			        	// Add E-Mail
			        	
			        	emailValues.put(Contacts.ContactMethods.DATA,
			        			StCampiRubrica[4]);
			        	
			        	
			        	Uri emailUpdate = getContentResolver()
			        			.insert(emailUri, emailValues);
			        	if (emailUpdate == null) {
			        		return -1;
			        	}
		        	}
	        	
	        	
	        	// add email
	        	if (StCampiRubrica.length >= 6)
		        	if (StCampiRubrica[5].trim().length() != 0)
		        	{
			        	ContentValues emailValues = new ContentValues();
			        	Uri emailUri = Uri
			        			.withAppendedPath(
			        					newPersonUri,
			        					Contacts.People.ContactMethods
			        	                                       .CONTENT_DIRECTORY);
			        	emailValues.put(Contacts.ContactMethods.KIND,
			        			Contacts.KIND_EMAIL);
			        	emailValues.put(Contacts.ContactMethods.TYPE,
			        			Contacts.ContactMethods.TYPE_HOME);
			        	// Add E-Mail
			        	
			        	emailValues.put(Contacts.ContactMethods.DATA,
			        			StCampiRubrica[4]);
			        	
			        	
			        	Uri emailUpdate = getContentResolver()
			        			.insert(emailUri, emailValues);
			        	if (emailUpdate == null) {
			        		return -1;
			        	}
		        	}
	        	
	        	// TYPE_WORK
	        	if (StCampiRubrica.length >= 7)
		        	if (StCampiRubrica[6].trim().length() != 0)
		        	{
			        	ContentValues faxValues = new ContentValues();
			        	Uri faxUri = Uri.withAppendedPath(newPersonUri,
			        			Contacts.People.Phones
			        	                        .CONTENT_DIRECTORY);
			        	faxValues.put(Contacts.Phones.NUMBER,
			        			StCampiRubrica[6]);
			        	faxValues.put(Contacts.Phones.TYPE,
			        			Contacts.Phones.TYPE_WORK);
			        	Uri phoneUpdate = getContentResolver()
			        			.insert(faxUri, faxValues);
			        	if (phoneUpdate == null) {
			        		return -1;
			        	}
		        	}
	        	
	        	// add Phone Home
	        	if (StCampiRubrica.length >= 8)
		        	if (StCampiRubrica[7].trim().length() != 0)
		        	{
			        	ContentValues faxValues = new ContentValues();
			        	Uri faxUri = Uri.withAppendedPath(newPersonUri,
			        			Contacts.People.Phones
			        	                        .CONTENT_DIRECTORY);
			        	faxValues.put(Contacts.Phones.NUMBER,
			        			StCampiRubrica[7]);
			        	faxValues.put(Contacts.Phones.TYPE,
			        			Contacts.Phones.TYPE_HOME );
			        	Uri phoneUpdate = getContentResolver()
			        			.insert(faxUri, faxValues);
			        	if (phoneUpdate == null) {
			        		return -1;
			        	}
		        	}
	        	
	        	// add fax number work
	        	if (StCampiRubrica.length >= 9)
		        	if (StCampiRubrica[8].trim().length() != 0)
		        	{
			        	ContentValues faxValues = new ContentValues();
			        	Uri faxUri = Uri.withAppendedPath(newPersonUri,
			        			Contacts.People.Phones
			        	                        .CONTENT_DIRECTORY);
			        	faxValues.put(Contacts.Phones.NUMBER,
			        			StCampiRubrica[8]);
			        	faxValues.put(Contacts.Phones.TYPE,
			        			Contacts.Phones.TYPE_FAX_WORK);
			        	Uri phoneUpdate = getContentResolver()
			        			.insert(faxUri, faxValues);
			        	if (phoneUpdate == null) {
			        		return -1;
			        	}
		        	}
	        	
	        	// add mobile phone number
	        	if (StCampiRubrica.length >= 10)
		        	if (StCampiRubrica[9].trim().length() != 0)
		        	{
			        	ContentValues mobileValues = new ContentValues();
			        	Uri mobileUri = Uri.withAppendedPath(newPersonUri,
			        			Contacts.People.Phones.CONTENT_DIRECTORY);
			        	// add mobile phone
			        	mobileValues.put(Contacts.Phones.NUMBER,
			        			StCampiRubrica[9]);
			        	mobileValues.put(Contacts.Phones.TYPE,
			        			Contacts.Phones.TYPE_OTHER );
			        	Uri phoneUpdate = getContentResolver()
			        			.insert(mobileUri, mobileValues);
			        	if (phoneUpdate == null) {
			        		return -1;
			        	}
		        	}
	        	
	        	// add mobile phone number
	        	if (StCampiRubrica.length >= 11)
		        	if (StCampiRubrica[10].trim().length() != 0)
		        	{
			        	ContentValues mobileValues = new ContentValues();
			        	Uri mobileUri = Uri.withAppendedPath(newPersonUri,
			        			Contacts.People.Phones.CONTENT_DIRECTORY);
			        	// add mobile phone
			        	mobileValues.put(Contacts.Phones.NUMBER,
			        			StCampiRubrica[10]);
			        	mobileValues.put(Contacts.Phones.TYPE,
			        			Contacts.Phones.TYPE_MOBILE);
			        	Uri phoneUpdate = getContentResolver()
			        			.insert(mobileUri, mobileValues);
			        	if (phoneUpdate == null) {
			        		return -1;
			        	}
		        	}
	        	
	        	// Address Home
	        	if (StCampiRubrica.length >= 12)
		        {
		        		
		        		Address = StCampiRubrica[11];
		        		// Add City
		        		if (StCampiRubrica.length >= 14)
		        				Address = Address + " " + StCampiRubrica[13];
		        		
		        		if (StCampiRubrica.length >= 15)
		        				Address = Address + " " + StCampiRubrica[14];
		        		
		        		if (StCampiRubrica.length >= 16)
		        			Address = Address + " " + StCampiRubrica[15];
		        		
		        		if (StCampiRubrica.length >= 17)
		        			Address = Address + " " + StCampiRubrica[16];
		        		
			        	// add address
		        		if (Address.trim().length() != 0) {
				        	ContentValues addressValues = new ContentValues();
				        	Uri addressUri = Uri
				        			.withAppendedPath(
				        					newPersonUri,
				        					Contacts.People.ContactMethods
				        	                                         .CONTENT_DIRECTORY);
				        	addressValues.put(Contacts.ContactMethods.KIND,
				        			Contacts.KIND_POSTAL);
				        	addressValues.put(Contacts.ContactMethods.TYPE,
				        			Contacts.ContactMethods.TYPE_HOME);
				        	addressValues.put(Contacts.ContactMethods.DATA,
				        			Address);
				        	Uri addressUpdate = getContentResolver().insert(addressUri,
				        					addressValues);
		        	
		        	
				        	if (addressUpdate == null) {
				        		return -1;
				        	}
		        		}
		        }
	        	
	        	// Addres Work
	        	if (StCampiRubrica.length >= 17)
		        {
		        		
		        		Address = StCampiRubrica[11];
		        		
		        		if (StCampiRubrica.length >= 20)
		        				Address = Address + " " + StCampiRubrica[19];
		        		
		        		if (StCampiRubrica.length >= 21)
		        				Address = Address + " " + StCampiRubrica[20];
		        		
		        		if (StCampiRubrica.length >= 22)
		        			Address = Address + " " + StCampiRubrica[21];
		        		
		        		if (StCampiRubrica.length >= 23)
		        			Address = Address + " " + StCampiRubrica[22];
		        		
			        	// add address
		        		if (Address.trim().length() != 0) {
				        	ContentValues addressValues = new ContentValues();
				        	Uri addressUri = Uri
				        			.withAppendedPath(
				        					newPersonUri,
				        					Contacts.People.ContactMethods
				        	                                         .CONTENT_DIRECTORY);
				        	addressValues.put(Contacts.ContactMethods.KIND,
				        			Contacts.KIND_POSTAL);
				        	addressValues.put(Contacts.ContactMethods.TYPE,
				        			Contacts.ContactMethods.TYPE_WORK);
				        	addressValues.put(Contacts.ContactMethods.DATA,
				        			Address);
				        	Uri addressUpdate = getContentResolver().insert(addressUri,
				        					addressValues);
		        	
		        	
				        	if (addressUpdate == null) {
				        		return -1;
				        	}
		        		}
		        }
	        	
	        	
	        } 
	        else
	        {
	        	return -1;
	        }
	        
	        return 1;
	        /*
	         * End Add contact to Android	
	         */	
	
	}
	
	
	void ReadContact(String fileName)
	{
		/*
		 *  Read Contact from  Android
		 * 
		 */
		
		// Find exist a contact to Android Phone
        String[] projection = new String[] {
        		Contacts.People._ID,
                Contacts.People.NAME,
                Contacts.People.NUMBER,
             };

        //Get the base URI for the People table in the Contacts content provider.
		Uri contacts =  Contacts.People.CONTENT_URI;
		
		//Make the query. 
		Cursor cur = managedQuery(contacts,
		                projection, 	// Which columns to return 
		                null,    		// Which rows to return (all rows)
		                null,       	// Selection arguments (none)
		                null);
		

		 // address = cur.getString(postalAddress);
	
		if (cur.moveToFirst()) {
			
			String OutContacts;
			
			int nameColumn = cur.getColumnIndex(Contacts.People.NAME); 
	        int phoneColumn = cur.getColumnIndex(Contacts.People.NUMBER);
	        //int data = cur.getColumnIndex(Contacts.ContactMethods.DATA);
	        
	        OutContacts = "";
	        
	        do {
	        	
	        	OutContacts = OutContacts + cur.getString(nameColumn); 
	        	
	        	if (cur.getString(phoneColumn) != null) 
	        		OutContacts = OutContacts + "," + cur.getString(phoneColumn);
	        	/*
	        	if (cur.getString(postalAddress) != null) 
	        		OutContacts = OutContacts + "," + cur.getString(postalAddress);
	        	*/
	        
	        	OutContacts = OutContacts + "\n";
	        } while (cur.moveToNext());
			
	        TextLog(OutContacts,1 );
	        Android_TO_Sd(fileName, OutContacts);
	    }
	    

	}
	
	void Android_TO_Sd(String fileName, String OutContact)
	{
		/*
		 * 
		 *  Write File
		 *  File Save to SD OK 
		 */
		try { 
			File myFile = new File(fileName);
			myFile.createNewFile();
			FileOutputStream fOut =  new FileOutputStream(myFile);
			BufferedOutputStream bos = new BufferedOutputStream( fOut );
			bos.write(OutContact.getBytes());
			bos.close();
		} catch (Exception e) {
	    	   TextLog("Error IO File: " + e.getMessage(),0);
	    }
		
	}
	
	void Sd_TO_Android(String fileName)
	{
		/*
         *  Read File
         * 
         */
        String strLine;
        strLine = "Init \n";
        int k = 0;
        int j = 0;
        TextLog("File :" + fileName,1);
        TextLog("Import SD to Android Contacts. Wait please.",1);
	        try { // catches IOException below
	        	
	        	// ** File Read to SD OK
	        	File infile = new File(fileName);
	        	//StringBuffer buffer = new StringBuffer();
	        	String string = new String();
	        	BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(infile)));
	        	while ((string = reader.readLine()) != null) {
	        		strLine = strLine + string + "\n";
	        		if (j > 0)
	        			if (AddContact(string) > 0)
	        				k++;
	        		
	        		TextLog(".",2);
	        		
	        		j++;
	        	}
	        	strLine = "Fine Importzione. " + k ;
	        	TextLog(strLine,1);
	        	reader.close();
	       } catch (Exception e) {
	    	   TextLog("Error: " + e.getMessage(),0);
	    	   
	       }
	}
	
	int SelListener = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        TxFileName = (EditText) findViewById(R.id.TxFileName);
        final Button but_start 		= (Button) findViewById(R.id.ButStart );
        final Button but_selfile 	= (Button) findViewById(R.id.ButSelFile );
        
        //final EditText TxFileName 	= (EditText) findViewById(R.id.TxFileName );
        TextLog("Start application..",0);
        
        but_selfile.setOnClickListener(new OnClickListener() {
    	public void onClick(View v) {
         		TextLog("Sel File ....",0);
         		
         		String fileName = TxFileName.getText().toString();
         		// Note the different intent: PICK_DIRECTORY
        		Intent intent = new Intent(FileManagerIntents.ACTION_PICK_FILE);
        		
        		// Construct URI from file name.
        		intent.setData(Uri.parse("file://" + fileName));
        		
        		// Set fancy title and button
        		intent.putExtra(FileManagerIntents.EXTRA_TITLE, getString(R.string.open_title));
        		intent.putExtra(FileManagerIntents.EXTRA_BUTTON_TEXT, getString(R.string.open_button));
        		
        		try {
        			startActivityForResult(intent, REQUEST_CODE_PICK_FILE_OR_DIRECTORY);
        		} catch (ActivityNotFoundException e) {
        			//No compatible file manager was found.
        			Toast.makeText(phonesync.this, R.string.no_filemanager_installed, 
        					Toast.LENGTH_SHORT).show();
        			
        		}
        		
        		
             }
         });
        
        // Click Button 
        but_start.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		TextLog("Read file.. Sd To Android",0);
        		
        		if (SelListener == 0) 
        			Sd_TO_Android(TxFileName.getText().toString());
        		else	
        			ReadContact(TxFileName.getText().toString());
        		       		
            }
        });
		
        
        // Radio Button
        OnClickListener radio_listener = new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                RadioButton rb = (RadioButton) v;
                
                if (rb.getTag().toString().compareTo("0") == 0) 
        			SelListener = 0;
        		else
        			SelListener = 1;
        		
                Toast.makeText(phonesync.this, rb.getText(), Toast.LENGTH_SHORT).show();
                
            }
        };
        
        final RadioButton SD_to_and = (RadioButton) findViewById(R.id.SD_to_and);
        final RadioButton And_to_SD = (RadioButton) findViewById(R.id.And_to_SD);
        SD_to_and.setOnClickListener(radio_listener);
        And_to_SD.setOnClickListener(radio_listener);
        // Set default value
        SD_to_and.setChecked(true);
    }
    
    /**
     * This is called after the file manager finished.
     */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		case REQUEST_CODE_PICK_FILE_OR_DIRECTORY:
			if (resultCode == RESULT_OK && data != null) {
				// obtain the filename
				String filename = data.getDataString();
				if (filename != null) {
					// Get rid of URI prefix:
					if (filename.startsWith("file://")) {
						filename = filename.substring(7);
					}
					
					Toast.makeText(phonesync.this, filename, 
	    					Toast.LENGTH_SHORT).show();
					
					TxFileName.setText(filename);
				}				
				
			}
			break;
		}
	}
}