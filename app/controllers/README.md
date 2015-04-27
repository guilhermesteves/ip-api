# App

## Controllers

This package contains all the actions of Controllers

## Auth Folder

This contains all authentications Controllers, in order of importance:

### AdminAuthentications

All authentication methods for Admins

### ModAuthentications

All authentication methods for Mods

### Cors

Anypath with OPTIONS will use this class

## Controllers Folder

### ApplicationController

All methods for simple routes that dont require any logic in models should be here

### AdminController

All methods for manipulating Admins belongs here in this Controller

### ModController

All methods for manipulating Mods belongs here in this Controller

### SettingsController

All methods for define settings that will change the behavior of the application must be here
