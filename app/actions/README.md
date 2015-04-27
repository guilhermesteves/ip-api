# App

## Actions Folder

This package contains all the actions (and annotations) for Controllers

### Error

**ErrorPolicy** and **ErrorAction** are used to handle errors and return them in a response

### Cors

**CorsPolicy** and **CorsAction** refer to the Cors options

### Admin Policy

The user needs to be an **Admin** (see models/users folder/Admin) in order to perform the action annotated with **AdminAction**

All the business concerning admins authentications is in **AdminPolicy** and **AdminAuth** (in models/users/auth/AdminAuth)

### Mod Policy

The user needs to be an **Mod** (see models/users/Mod) in order to perform the action annotated with **ModAction**

All the business concerning mods authentications is in **ModPolicy** and **ModAuth** (in models/users/auth/ModAuth)

### User Policy

The user needs to be an **User** (see models/users/User) in order to perform the action annotated with **UserAction**

All the business concerning users authentications is in **UserPolicy** and **UserAuth** (in models/users/auth/UserAuth)

### Client Policy

In order to protect this API, the client (or requester) must send a BASIC authorization in order to get public content, that's why all public routes have the **ClientAction**

All **ClientPolicy** do is to check if you happen to know any of the UUID from **ClientApp** (in models/common/constants)

#### Anything else?

Pretty straight forward, huh?