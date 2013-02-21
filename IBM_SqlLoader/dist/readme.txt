If you clone the repository first, please follow the instruction.


1. To see all files properly including files starting with dot(.), choose "Customize View > filters" or "filters"
in the package explorer or enterprise explorer and uncheck ".* resource".

2. remove all "-dist" suffix from the file name 

3. Copy all files in this folder into the proper one.

	.classpath -> <project_root>/.classpath
	org.eclipse.wst.common.project.facet.core.xml -> <project_root>/.settings/org.eclipse.wst.common.project.facet.core.xml

   These files should be included in .gitignore, so these won't be synced.
   
4. Fix "Java build path" and "Project facets" if necessary.  
	ex) If you are going to run it on liberty env, you need to fix them properly


