# SiteMap
Android App for displaying and managing Archaeological Sites 

## Set up

* Create a firebase project and connect it to the app. You need to activate the authentication with email and password, firestore and Storage.
    * **Note:** Remember to edit the rules as follows:
            
             rules_version = '1';
                service cloud.firestore {
                    match /databases/{database}/documents {
                        match /{document=**} { 
                            allow read, write: if request.auth.uid != null;
                        }
                    }
                }
    * Any doubts, consult the oficial documentation: https://firebase.google.com/docs/android/setup?authuser=0
* Access your google cloud project and activate the Maps SDK for Android API. Create a file "google_maps_api.xml" inside src>debug>res>values and add the following:

         <?xml version="1.0" encoding="utf-8"?>
            <resources>
                <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false"><YOUR KEY HERE></string>
            </resources>
## Author

Aylton Almeida
