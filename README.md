[![](https://jitpack.io/v/joyner-perez/aes-dukpt-android.svg)](https://jitpack.io/#joyner-perez/aes-dukpt-android)
# Android AES DUKPT Library with Secure Shared Preferences

Implementation of the ANSI AES DUKPT standard: specified within Retail Financial Services Symmetric Key Management Part 3: Using Symmetric Techniques (ANSI X9.24-3:2017).

**Note: Only DATA and PIN encryption.**

**Show app folder example for more help**

**With Kotlin**

⚙️ How to install
--
1. Add it in your root build.gradle at the end of repositories:

		allprojects {
			repositories {
				...
				maven { url 'https://jitpack.io' }
			}
		}

2. Add the dependency:

		dependencies {
			implementation 'com.github.joyner-perez:aes-dukpt-android:1.3.0'
		}

🕹 How to use with Kotlin
--
	val implDukpt = instance
	val result = implDukpt!!.saveInitialKey(this, "test", "1273671EA26AC29AFA4D1084127652A1", KType.AES128, "1234567890123456")
	Log.d("CREATED INITIAL KEY", if (result) "SUCCESS" else "ERROR")
	
	val encriptedResult = implDukpt.encriptDataWithDUKPT(this, "test", "A912150391AB65A67E52883D81CE2D15", EncriptVariant.PIN, KType.AES128)
	if (encriptedResult != null) {
		Log.d("DATA ENCRYPTED", encriptedResult.dataEncripted)
		Log.d("KSN VALUE", encriptedResult.ksnUsed)
	}

🕹 How to use with Java
--
	ImplDukpt implDukpt = ImplDukpt.getInstance();
	boolean result = implDukpt.saveInitialKey(this, "test", "1273671EA26AC29AFA4D1084127652A1", KType.AES128, "1234567890123456");
	Log.d("CREATED INITIAL KEY", result ? "SUCCESS" : "ERROR");

	EncriptedResult encriptedResult = implDukpt.encriptDataWithDUKPT(this, "test", "1234567890", EncriptVariant.DATA, KType.AES128);
	if (encriptedResult != null) {
	    Log.d("DATA ENCRYPTED", encriptedResult.getDataEncripted());
	    Log.d("KSN VALUE", encriptedResult.getKsnUsed());
	}

👀 **Be careful**

Derived Working Key | AES-128 BDK | AES-192 BDK | AES-256 BDK
------------------- |-------------|-------------|------------
2TDEA Working Key   |   Allowed   |   Allowed   |   Allowed
3TDEA Working Key   |   Allowed   |   Allowed   |   Allowed
AES-128 Working Key |   Allowed   |   Allowed   |   Allowed
AES-192 Working Key | Not Allowed |   Allowed   |   Allowed
AES-256 Working Key | Not Allowed | Not Allowed |   Allowed
