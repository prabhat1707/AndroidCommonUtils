# AndroidCommonUtils
Android Util Library Contain a Small Collection of libraries that we commonly use in our android projects. In the current version, it contains OtpView with SmsRetriver Api and Phone number util with phone number Picker and also Parse a phone number with phone code. 

## What's New in Ver 1.0

1. Phone number Util
    - Parse a phone number with a phone code.
    - Phone Picker with Country flag and phone Code.

2. Otp View
    - OTP custom view Tag with different features.
    - Auto Read Sms by using SMS Retriever API.
    
# Sample Images:
![IMages1](https://firebasestorage.googleapis.com/v0/b/chatapp-2e1df.appspot.com/o/android%20util%20images%2Fotp_rezied.gif?alt=media&token=24433b69-70cc-40c4-abae-1a84188e241f)
![alt Setting IMages2](https://firebasestorage.googleapis.com/v0/b/chatapp-2e1df.appspot.com/o/android%20util%20images%2Fphone_rezied.gif?alt=media&token=4c6800cd-c4e8-49ee-9a82-0551d57fc018)

# Prerequisites
- Android 17
# Installing
## Step 1:- Add it in your root build.gradle at the end of repositories:

````
all projects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
  
````
## Step 1:- Add the dependency:
````
        dependencies {
            	   implementation 'com.github.prabhat1707:AndroidCommonUtils:1.0'
        }
    
````

## How to use the OTP Util library?

Add the following to your XML design to show the otpview

````
  <com.androidutillibrary.otputil.OtpView
        android:id="@+id/otp_view"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="#000"
        app:bar_enabled="true"
        app:bar_height="1.5dp"
        app:bar_margin_bottom="0dp"
        app:bar_margin_left="2dp"
        app:bar_margin_right="2dp"
        app:bar_success_color="@color/blue"
        app:height="40dp"
        app:otp_box_background="@color/box_color"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:length="6"
        app:otp=""
        app:otp_text_size="20dp"
        app:width="40dp" />

````

Get a callback when the user starts to enter and complete the OTP

````
OtpView.setOtpListener(new OTPListener() {
	@Override
	public void onUserInteraction() {
	// start when user types something in the Otpbox
	}
	@Override
	public void onOTPComplete(String otp) {
	// start when user has entered the OTP fully.
	    Toast.makeText(MainActivity.this, "The OTP is " + otp,  Toast.LENGTH_SHORT).show();
    }
});

````
Some Additional Methods

````

OtpView.setOTP(otpString);	// sets the entered otpString 
OtpView.getOTP();	// retrieves the OTP entered by user 
OtpView.showSuccess();	// shows the success state to the user 
OtpView.showError();	// shows the Error state to the user 
OtpView.resetState();

````

#### OtpView Attributes

| Attribute | Description |
| --- | --- |
| `android:textColor` | set text color ot otp |
| `app:length` | set lenght of the otp |
| `app:hide_otp` | set whether shoe=w or hide the otp |
| `app:hide_otp_drawable` | set your own custom hide otp drawable |
| `app:height` | set height of each box |
| `app:width` | set width of each box  |
| `app:bar_enabled` | below bar in each otp bpx enable or not |
| `app:bar_height` | set height of below bar |
| `app:bar_margin` | sets the space between each box in otp view |
| `app:bar_active_color` | bar active color when otp is entered |
| `app:bar_inactive_color` | bar inactive color when otp is already entered |
| `app:bar_error_color` | show wrong otp error by set bar error color |
| `app:bar_success_color` | show success otp by set bar success color|
| `app:otp_box_background` | set background of otp box |

### Add the below code to enable Auto Sms Retriever or Autofill OTP code

````
AutoReadSmsUtil.setSmsListener(WeakReference(this)) // sms listener

````

Add Broadcast in manifest

````
      <receiver
            android:name="com.androidutillibrary.OtpView.MySmsRetriever"
            android:exported="true"
            android:permission="com.google.android.gms.auth.api.phone.permission.SEND">
            <intent-filter>
                <action android:name="com.google.android.gms.auth.api.phone.SMS_RETRIEVED" />
            </intent-filter>
      </receiver>

````

Add Broadcast inactivity to receive data and message

````
 private val smsBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action.equals(MySmsRetriever.SMSLOCALBROADCASTE)){
                val data = intent?.getBundleExtra("extra") -- > this giev you all data or code related to sms
                val mess  = intent?.getStringExtra(MySmsRetriever.SMS_DATA) -> if only want only messsage from sms then use this only
                otpView?.setOTP(mess?.getOtpCode(6)!!)
            }
        }
    }
   
````
#### Otp code retriver Helper

use below one if your mess contains integer code with x digit else you can make your logic to extract it.

Sample SMS 

````
Your OTP Code is: 123456


FA+9qCX9VSu --> SMS Signature 

````

````
mess?.getOtpCode(no fo int code in mess )!!

````

#### How to get SMS Signature and why we need this?

First , read this blog to understand in detail <a href="https://developers.google.com/identity/sms-retriever/overview">Click Here</a>

To get Sms Signature use the below code

````
 val sig = AutoReadSmsUtil.getSmsAppSignature(WeakReference(this))

````

## How to Use FlagView Util

Add the following to your XML design to show the otpview

````
<com.androidutillibrary.phonenumberiutil.FlagView
        android:id="@+id/flag_view"
        android:layout_width="wrap_content"
        android:background="#fff"
        app:enableCountryPicker = "true"
        app:enablePhoneCode = "true"
        app:enableCountryFlag = "true"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="@+id/editTextTextPersonNumber"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="@+id/editTextTextPersonNumber">
      
````
#### FlafView Attributes

| Attribute | Description |
| --- | --- |
| `app:enableCountryPicker` | setr to eanble pciker or country code|
| `app:enablePhoneCode` | show phoneCode or not |
| `app:enableCountryFlag` | show country flag or not|
| `android:background` | set background color |

Use the below code to parse phone number

````
// in kotlin
flag_view.getFormatNumber("+910123456789")?.apply {
            code.text = "Phone Code is " + phoneCode
            phone_number.text = "Phone Number is " + formattedNumber
            editTextTextPersonNumber.setText(formattedNumber)
        }
        
// in java

ParsedNumberData c = flag_view.getFormatNumber("+910123456789")

````

What ParsedNumberData Contains

````
coutryCode -> it gives you country code of that phone number
phoneCode -> it gives you phone code with + if you number contain 
formattedNumber -> parse mobile number
phoneNumberCode -> only phone code without +
countryName -> country name of that phone number
int loadFlagByCode(Context c) -> give you flat int id just call it.

````

How to Open country code Picker?

````
    // but make sure first enable Country picker like in XML or code
    
        app:enableCountryPicker = "true" --> in xml
        flag_view.enableCountryCodePicker(true) --> in code
     
      flag_view.setCountryPickerListener(CountryPicker.OnCountryPickerListener { country ->
            Toast.makeText(this,country.phoneCode +","+country.countryName,Toast.LENGTH_SHORT).show()
        })

````

#### Bugs, Feature requests

Found a bug? Something that's missing? Feedback is an important part of improving the project, so, please
<a href="https://github.com/prabhat1707/AndroidCommonUtils/issues">open an issue</a>

# License

````
Copyright (c) delight.im <prabhat.rai1707@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable la
w or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

````
