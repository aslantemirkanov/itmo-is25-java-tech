rootProject.name = "aslantemirkanov"
include("banks")
include("banks-console")
include("Lab2")
include("Lab2:DataAccess")
findProject(":Lab2:DataAccess")?.name = "DataAccess"
include("Lab2:Application")
findProject(":Lab2:Application")?.name = "Application"
include("Lab2:Presentation")
findProject(":Lab2:Presentation")?.name = "Presentation"
include("Lab3")
include("Lab3:Presentation")
findProject(":Lab3:Presentation")?.name = "Presentation"
include("Lab3:Application")
findProject(":Lab3:Application")?.name = "Application"
include("Lab3:DataAccess")
findProject(":Lab3:DataAccess")?.name = "DataAccess"
include("Lab3:Presentation")
findProject(":Lab3:Presentation")?.name = "Presentation"
include("Lab3:Security")
findProject(":Lab3:Security")?.name = "Security"
include("Lab3:Security")
findProject(":Lab3:Security")?.name = "Security"
include("Lab5")
include("Lab5:CatService")
findProject(":Lab5:CatService")?.name = "CatService"
include("Lab5:CatOwnerService")
findProject(":Lab5:CatOwnerService")?.name = "CatOwnerService"
include("Lab5:UserService")
findProject(":Lab5:UserService")?.name = "UserService"
include("Lab5:DataService")
findProject(":Lab5:DataService")?.name = "DataService"
