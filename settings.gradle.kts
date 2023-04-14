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
