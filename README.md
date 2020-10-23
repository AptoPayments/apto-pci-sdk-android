# Apto Android PCI SDK

The Apto PCI SDK provides a transparent `View` that can display the PCI data using a `webView`.

This document provides an overview of how to:

* [Install the SDK](#user-content-install-the-sdk)
* [Initialize the SDK](#user-content-initialize-the-sdk)
* [Show / Hide the PCI Data](#user-content-show--hide-the-pci-data)
* [Customize the PCI View](#user-content-customize-the-pci-view)

For more information, see the [Apto Developer Guides](http://docs.aptopayments.com).

To contribute to the SDK development, see [Contributions & Development](#user-content-contributions--development)

## Requirements

* Android SDK, minimum API Level 23 (Marshmallow)
* Kotlin, minimum version 1.4.0
* Gradle, minimum version 4.0.1

**Note:** The SDK is built using Kotlin, but is fully interoperable with Java. Code adjustments may be needed, if used within a Java project.

### Get the Mobile API key

A Mobile API Key is required to run the SDK. To retrieve your Mobile API Key:

1. Register for an account or login into the [Apto Developer Portal](https://developer.aptopayments.com). 
    
2. Select **Developers** from the menu. Your **Mobile API Key** is listed on this page.

    ![Mobile API Key](readme_images/devPortal_mobileApiKey.jpg)

    **Note:** `MOBILE_API_KEY` is used throughout this document to represent your Mobile API key. Ensure you replace `MOBILE_API_KEY` with the Mobile API Key in your account.

## Install the SDK 

We suggest using [Gradle](https://gradle.org) to install the SDK:

1. In your project's `build.gradle` file, add the Apto repository:

```
allprojects {
  repositories {
    
    ...
    
    maven { url "https://dl.bintray.com/apto/maven" }
    
    ...
    
  }
}
```

2. In your app's `build.gradle`, add the following dependency:

```
implementation 'com.aptopayments.sdk:mobile:2.0.0'
```

## Initialize the SDK

To initialize the PCI SDK:

1. Add a `PCIView` to your layout. This example adds the PCI SDK view to the `activity_main.xml` located in the `app/src/main/res/layout` folder path. 

**Note:** Ensure you label the PCI view with an ID. This ID will be used to reference the view in the main Activity / Fragment file.

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android" xmlns:app="http://schemas.android.com/apk/res-auto" xmlns:tools="http://schemas.android.com/tools" android:id="@+id/main" android:layout_width="match_parent" android:layout_height="match_parent"
    tools:context=".MainActivity">

    <androidx.cardview.widget.CardView android:id="@+id/cardView" android:layout_width="match_parent" android:layout_height="wrap_content" android:layout_gravity="center" android:layout_margin="20dp" app:cardCornerRadius="8dp" app:layout_constraintTop_toTopOf="@id/main">
        
        ...
        
        <com.aptopayments.sdk.pci.PCIView
            android:id="@+id/pci_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </androidx.cardview.widget.CardView>
    
    ...
    
</androidx.constraintlayout.widget.ConstraintLayout>    
```

2. In a file that can access your `pci_view` component, create a `PCIConfigAuth` object. This manages the authentication configuration settings.

```kotlin
val configAuth = PCIConfigAuth(
	apiKey = "MOBILE_API_KEY",
	userToken = "USER_TOKEN",
	cardId = "ACCOUNT_ID",
	environment = PCIEnvironment.SBX
)
```

Parameter|Description
---|---
`apiKey`|This value is the Mobile API Key (`MOBILE_API_KEY`) retrieved from the [Apto Developer Portal](https://developer.aptopayments.com).
`userToken`|This value is the user session token (`USER_TOKEN`) retrieved from the login flow.
`cardId`|This value is the account ID (`ACCOUNT_ID`) of the card.
`environment`|This value is the deployment mode for your app. The available values are:<ul><li>`PCIEnvironment.SBX` - Sandbox mode</li><li>`PCIEnvironment.PRD` - Production mode</li></ul>

3. *(Optional)* Create a `PCIConfigCard` object, passing in the cardholder's name and the last four digits of the card. See [Display Card Data Elements](#user-content-display-card-data-elements) for more info.

```kotlin
val configCard = PCIConfigCard(nameOnCard = "NAME_ON_CARD", lastFour = "1234")
```

4. *(Optional)* Create a `PCIConfigStyle` object, passing any desired style settings. See [Style Card Elements](#user-content-style-card-elements) for more info.

```kotlin
val style = PCIConfigStyle(textColor = "FFFF00")
```

5. Create a `PCIConfig` object, passing in the `configAuth`, `configCard`, and/or `style` parameters. See [Display Card Data Elements](#user-content-display-card-data-elements) for more information about the `PCIConfig` object.

```kotlin
val pciConfig = PCIConfig(configAuth = configAuth, configCard = configCard, style = style)
```

5. Initialize the PCI view using `pci_view.init`, passing in the `pciConfig` as the `PCIConfig` object.

```kotlin
pci_view.init(pciConfig)
```

## Show / Hide the PCI Data

The PCI SDK enables you to:

* Show the card's complete data. To show the card's complete data, use the `showPCIData` method: `pciView.showPCIData()`.

	**Note:** The user will receive an SMS or email with a one-time passcode. This password must be entered into the displayed dialog box. If the passcode is correct, the PCI data will be shown.

* Show only the card's last four digits (if set). This hides all the PCI data. To hide all the PCI data except for the card's last four digits, use the `hidePCIData` method: `pciView.hidePCIData()`.

## Customize the PCI View

The PCI SDK uses configuration objects to change the default PCI configuration. The following elements can be customized:

* [Display Card Data Elements](#user-content-display-card-data-elements)
* [Style Card Elements](#user-content-style-card-elements)
* [Style Alerts](#user-content-style-alerts)

### Display Card Data Elements

The `PCIConfigCard` object is used to set card configurations. The following configurations are available:

Property|Description
---|---
`lastFour`|String value used as placeholders for the last 4 digits of the card when hidden. The default value is: `••••`
`labelPan`|String value specifying the text for the PAN (Primary Account Number) description label. The default value is an empty string.
`labelCvv`|String value specifying the text for the CVV description label. The default value is `CVV`.
`labelExp`|String value specifying the text for the expiration date description label. The default value is `EXP`.
`nameOnCard`|String value specifying the name displayed on the card. The default value is an empty string.
`labelName`|String value specifying the text for the name description label. The default value is an empty string.

### Style Card Elements


TODO This will be completed in a following PR


### Style Alerts

The PCI SDK enables you to:

* [Style Alert Text](#user-content-style-alert-text)
* [Style Alert Buttons](#user-content-style-alert-buttons)

#### Style Alert Text

To customize the text shown in the alerts, override the following values in your `strings.xml` file.

Text Option|Description
---|---
`pcisdk_input_code_message`|A string value specifying the Alert message text.
`pcisdk_input_code_ok`|A string value specifying the OK button text.
`pcisdk_input_code_cancel`|A string value specifying the cancel button text.
`pcisdk_wrong_code_message`|A string value specifying the message displayed, when the user enters a wrong code.
`pcisdk_wrong_code_ok`|A string value specifying the OK button text, when the user enters a wrong code.

#### Style Alert Buttons

An alert button appears to prompt the user to enter their SMS / email code, and when the user incorrectly enters the code. By default, the button color uses your app's configured `colorAccent` value.

The background color of this button can be customized by setting the `alertButtonColor` attribute to an `Int` color value:

```kotlin
pciView.alertButtonColor = yourIntColor
```

## Contributions & Development

We look forward to receiving your feedback, including new feature requests, bug fixes and documentation improvements.

If you would like to help: 

1. Refer to the [issues](https://github.com/AptoPayments/apto-pci-sdk-android/issues) section of the repository first, to ensure your feature or bug doesn't already exist (The request may be ongoing, or newly finished task).
2. If your request is not in the [issues](https://github.com/AptoPayments/apto-pci-sdk-android/issues) section, please feel free to [create one](https://github.com/AptoPayments/apto-pci-sdk-android/new). We'll get back to you as soon as possible.

If you want to help improve the SDK by adding a new feature or bug fix, we'd be happy to receive [pull requests](https://github.com/AptoPayments/apto-pci-sdk-android/compare)!

## License

AptoPCISDK is available under the MIT license. See the [LICENSE file](https://github.com/AptoPayments/apto-pci-sdk-android/AptoPCISDK/LICENSE) for more info.