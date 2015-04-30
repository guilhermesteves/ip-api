# App

## Models

### Common

#### Generic

God... are you there? You need to see these.

##### The Mother of All Abstractions

Right now, we have a layer of abstraction on how we can save a model (as how it belongs in the database - without or with parent) and an abstraction over this where you define common methods to all ORM in **DefaultDAO** and **DefaultDAOImpl** and can be implemented the Abstract Layer

##### The Abstraction Layer

Well, 2 DAOs ( **SimpleDAO** and **MultiTenancyDAO** ) extends **DefaultDAO** and make their own set of abstractions. Those specific/model DAO whose will be a non partent should extend **SimpleDAO** and the childs **MultiTenancyDAO**.

Keep in mind that all simplification can come in handy, as well has it's cost.

In each case, they must be registered in it's own belonging Factory to be used (check factory folder).