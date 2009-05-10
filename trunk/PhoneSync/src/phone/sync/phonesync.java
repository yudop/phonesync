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

import android.app.Dialog;
import android.app.ProgressDialog;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class phonesync extends Activity {
	

	static final int PROGRESS_DIALOG = 0;
    ProgressDialog progressDialog;
	protected static final int REQUEST_CODE_PICK_FILE_OR_DIRECTORY = 1;
	protected EditText TxFileName;
    
    protected Dialog onCreateDialog(int id) {
        switch(id) {
        case PROGRESS_DIALOG:
            progressDialog = new ProgressDialog(phonesync.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Working...");
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(true);
            return progressDialog;
        default:
            return null;
        }
    }

        
	
	String[] baseProjection = new String[] {
            android.provider.BaseColumns._ID,
            android.provider.Contacts.PeopleColumns.DISPLAY_NAME,
            android.provider.Contacts.PeopleColumns.NOTES,
            android.provider.Contacts.PeopleColumns.STARRED
    };
    
    String[] contactMethodsProjection = new String[] {
            android.provider.BaseColumns._ID,
            android.provider.Contacts.ContactMethodsColumns.DATA,
            android.provider.Contacts.ContactMethodsColumns.AUX_DATA,
            android.provider.Contacts.ContactMethodsColumns.KIND,
            android.provider.Contacts.ContactMethodsColumns.TYPE
    };
    
    String[] phonesProjection = new String[] {
            android.provider.BaseColumns._ID,
            android.provider.Contacts.PhonesColumns.LABEL,
            android.provider.Contacts.PhonesColumns.NUMBER,
            android.provider.Contacts.PhonesColumns.NUMBER_KEY,
            android.provider.Contacts.PhonesColumns.TYPE      
    };
    
    
    public class User 
    {
        String title;
        String name;
        String company;
        String notes;
        String photo;
        boolean starred;
        List contactMethods = new ArrayList();
        List phones = new ArrayList();
    }

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
			        			StCampiRubrica[5]);
			        	
			        	
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
	
	/*
	 *  Read Contact from  Android
	 * 
	 */
	
	
	void ReadContact(String fileName, int export_mode)
	{
		/*
		 * 
		 * Output: 
	     *        stContact - Format in .CSV
	     		0  Id,
	        	1  Nome visualizzato,
	        	2  Soprannome,
	        	3  company,
	        	4  Telefono 1,
	        	5  Telefono 2,
	        	6  Email primaria,
	        	7  Email secondaria,
	        	9  Indirizzo di casa,
	        	10 Indirizzo di casa 2,
	        	11 Note
	     *        
		 */
		
		String OutContacts;
		OutContacts = doUserContent (export_mode);
		Android_TO_Sd(fileName, OutContacts);
	}
	
	String AllPhones (Cursor cursor, int export_mode)
    {
		String StOut = "";
		int number_count = 0;
		
		while (cursor.moveToNext())
        {  
                String label = cursor.getString(cursor.getColumnIndex(Contacts.PhonesColumns.LABEL));
                String number = cursor.getString(cursor.getColumnIndex(Contacts.PhonesColumns.NUMBER));
                int type = cursor.getInt(cursor.getColumnIndex(Contacts.PhonesColumns.TYPE));
                String encodedNumber = number;
                try
                {
                		encodedNumber = URLEncoder.encode(number, "UTF-8");             
                }
                catch (Exception e)
                {
                        Log.w("Jetty", "Encoding telephone number failed");
                }
                
                if (export_mode == 1)
                {
	                switch (type) {
	                	case Contacts.Phones.TYPE_MOBILE:
	                		StOut =  StOut + "MOBILE :";
	                		break;
	                	
	                	case Contacts.Phones.TYPE_HOME:
	                		StOut =  StOut + "HOME :";
	                		break;
	                		
	                	case  Contacts.Phones.TYPE_WORK:
	                		StOut =  StOut + "WORK :";
	                		break;
	                	
	                	case  Contacts.Phones.TYPE_FAX_HOME:
	                		StOut =  StOut + "FAX_HOME :";
	                		break;
	                	
	                	case  Contacts.Phones.TYPE_FAX_WORK:
	                		StOut =  StOut + "FAX_WORK :";
	                		break;
	                	
	                	default:
	                		StOut =  StOut + "OTHER :";
	            			break;
	                		
	                }
	                
	                if (label != null)
	                	StOut = StOut + label + ";";
	                
	                StOut =  StOut + " " + number+ "\n";
                }
                else
                {
                	
                	number_count ++;
                	if (number_count <= 2)
                		StOut =  StOut + number+ ",";
                	else
                		return StOut;
                }
        }
		
		if (export_mode == 2)
		{
			for (int i=number_count; i <= 2; i++)
				StOut =  StOut + ",";
		}
		
        return StOut;
    }

	String ContactMethods (Cursor cursor, int export_mode)
    {
		String StOut = "";
		String StMail = "";
		String StPostal = "";
		
		int count_email = 0;
		int count_POSTAL = 0;
		
        while (cursor.moveToNext())
        { 
            String data = cursor.getString(cursor.getColumnIndex(Contacts.ContactMethodsColumns.DATA));
            String auxData = cursor.getString(cursor.getColumnIndex(Contacts.ContactMethodsColumns.AUX_DATA));
           
            int kind = -99;
            int type = -99;
            
            if (data!=null||auxData!=null)
            {
                kind = cursor.getInt(cursor.getColumnIndex(Contacts.ContactMethodsColumns.KIND));
                type = cursor.getInt(cursor.getColumnIndex(Contacts.ContactMethodsColumns.TYPE));
            }
            
            if (export_mode == 1)
            {
	            switch (kind) {
		        	case Contacts.KIND_EMAIL:
		        		StOut =  StOut + "EMAIL :";
		        		break;
		        	
		        	case Contacts.KIND_POSTAL:
		        		StOut =  StOut + "POSTAL :";
		        		break;
		        		
		        	case Contacts.KIND_IM:
		        		StOut =  StOut + "IM :";
		        		break;
		        		
		        	default:
		        		StOut =  StOut + "OTHER :";
		    			break;
		        		
		        }
	        
	            if (data!=null)
	                		StOut =  StOut + data;
	            if (auxData!=null)
	                		StOut =  StOut + data;
	            
	            
	            StOut =  StOut + "\n";
            }
            else
            {
            	switch (kind) {
		        	case Contacts.KIND_EMAIL:
		        		count_email ++; 
		        		if (count_email <= 2)
		        			StMail = StMail + data + ",";
		        		break;
		        	
		        	case Contacts.KIND_POSTAL:
		        		count_POSTAL ++;
		        		if (count_POSTAL <= 2)
		        			StPostal =  StPostal + data+ ",";
		        		break;	
            	}
            }
        }
        
        
        if (export_mode == 2)
		{
			for (int i=count_email; i <= 2; i++)
				StMail = StMail + ",";
			
			for (int i=count_email; i <= 2; i++)
				StPostal =  StPostal + ",";
			
			StOut = StMail + StPostal;
		}
        
        
        return StOut;
    }  
	
	String doUserContent(int export_mode)
    {
		
		String OutContacts = "";
		String SingheContact = "";
		Cursor cursor;
		Cursor cursorContacts;
		
        //query for the user's standard details
		//cursor = getContentResolver().query(Contacts.People.CONTENT_URI, baseProjection, "people."+android.provider.BaseColumns._ID+" = 99", null, Contacts.PeopleColumns.NAME+" ASC");
        //OutContacts = UserDetails(cursor);
        //cursor.close();
        
		cursorContacts = getContentResolver().query(Contacts.People.CONTENT_URI, baseProjection, null, null, Contacts.PeopleColumns.NAME+" ASC");
		
		cursorContacts.moveToFirst();
		do {
			
			//User details
			String id = cursorContacts.getString(cursorContacts.getColumnIndex(android.provider.BaseColumns._ID));  
            String name =  cursorContacts.getString(cursorContacts.getColumnIndex(Contacts.PeopleColumns.DISPLAY_NAME));
            String company = null;
            String notes = cursorContacts.getString(cursorContacts.getColumnIndex(Contacts.PeopleColumns.NOTES));
            if (export_mode == 1) 
            	SingheContact = "ID:" + id + "\n" +  name + "\n";
            else
            	SingheContact = id + "," +  name + ",";
            
            if (company!=null)
            {
            	if (export_mode == 1)
            		SingheContact = SingheContact + company + "\n";
            	else
            		SingheContact = SingheContact + company + ",";
            }
            	else
            		SingheContact = SingheContact + ",";
            
	        //query for all phone details
	        cursor = getContentResolver().query(Contacts.Phones.CONTENT_URI, phonesProjection, "people."+android.provider.BaseColumns._ID+" = " + id , null, Contacts.PhonesColumns.TYPE+" ASC");
	        SingheContact = SingheContact + AllPhones (cursor, export_mode);
	        cursor.close();
	        
	        //query for all contact details
	        cursor = getContentResolver().query(Contacts.ContactMethods.CONTENT_URI, contactMethodsProjection, "people."+android.provider.BaseColumns._ID+" = " + id, null, Contacts.ContactMethodsColumns.KIND +" DESC");
	        SingheContact = SingheContact + ContactMethods (cursor, export_mode);
	        cursor.close(); 
	        
	        if (notes!=null)
	        	if (export_mode == 1)
	        		SingheContact = SingheContact + "Notes:" + notes + "\n";
	        	else
	        		SingheContact = SingheContact + notes;
	        
	        
	        OutContacts = OutContacts + SingheContact + "\n";
	        
    	} while (cursorContacts.moveToNext());
		
        cursorContacts.close();	        
        return OutContacts;
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
			//File myFile = new File("/sdcard/PhoneSync/Export.txt");
			myFile.createNewFile();
			FileOutputStream fOut =  new FileOutputStream(myFile);
			BufferedOutputStream bos = new BufferedOutputStream( fOut );
			bos.write(OutContact.getBytes());
			bos.close();
		} catch (Exception e) {
			 Toast.makeText(phonesync.this, "Error: " + e.getMessage(), 
	   					Toast.LENGTH_SHORT).show();
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
	        try { // catches IOException below
	        	
	        	// ** File Read to SD OK
	        	File infile = new File(fileName);
	        	String string = new String();
	        	BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(infile)));
	        	while ((string = reader.readLine()) != null) {
	        		strLine = strLine + string + "\n";
	        		if (j > 0)
	        			if (AddContact(string) > 0)
	        				k++;
	        		
	        		j++;
	        	}
	        	strLine = "Fine Importzione. " + k ;
	        	//TextLog(strLine,1);
	        	reader.close();
	       } catch (Exception e) {
	    	   Toast.makeText(phonesync.this, "Error: " + e.getMessage(), 
   					Toast.LENGTH_SHORT).show();  
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
        		showDialog(PROGRESS_DIALOG);
        		if (SelListener == 0) 
        		{
        			TextLog("Read file.. Sd To Android",0);
        			new Thread(new Runnable() {
        			    public void run() {
        			    	Sd_TO_Android(TxFileName.getText().toString());
    				    	dismissDialog(PROGRESS_DIALOG);
    				    	
        			    }
        			 }).start();
        		}
        			else
        		{
        			TextLog("Write file.. Android To Sd",0);
        			new Thread(new Runnable() {
        			    public void run() {
        			    	ReadContact(TxFileName.getText().toString(), SelListener);
    				    	dismissDialog(PROGRESS_DIALOG);
        			    }
        			  }).start();
        		} 
        		
        		
            }
        });
		
                
        // Radio Button
        OnClickListener radio_listener = new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
                RadioButton rb = (RadioButton) v;
                if (rb.getTag().toString().compareTo("0") == 0) 
                    SelListener = 0;

                if (rb.getTag().toString().compareTo("1") == 0) 
                    SelListener = 1;
                
                if (rb.getTag().toString().compareTo("2") == 0) 
                    SelListener = 2;
                
                Toast.makeText(phonesync.this, rb.getText(), Toast.LENGTH_SHORT).show();
            }
        };
        
        
        final RadioButton SD_to_and = (RadioButton) findViewById(R.id.SD_to_and);
        final RadioButton And_to_SD = (RadioButton) findViewById(R.id.And_to_SD);
        final RadioButton And_to_SD_CVS = (RadioButton) findViewById(R.id.And_to_SD_CVS);
        SD_to_and.setOnClickListener(radio_listener);
        And_to_SD.setOnClickListener(radio_listener);
        And_to_SD_CVS.setOnClickListener(radio_listener);
        
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