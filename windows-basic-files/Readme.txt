Introducci�n
-----------
"Quien posea el poder de invocar toda clase de criaturas inimagiables, tiene el mismo poder que un dios."

Este programa, no es m�s que un grimorio digital, un libro que permita invocar h�roes, criaturas o demonios. Pretendo que cada lector pueda incluir sus p�ginas, a su gusto, permitiendo as� crear todos los personajes que deseen para sus partidas. 
El Libro de Esher ha sido desarrollado por Jorge Hortelano Otero. Espero que te sea realmente �til a la hora de evitar el tedioso trabajo de realizar un PJ o simplemente te ayude con tus PNJs. 
Lo distribuyo como software libre, asi que puedes utilizarlo o modificarlo a tu gusto mientras dejes estas palabras y el nombre del autor original (osease, yo).  Para m�s informaci�n leete la licencia GNU en el fichero adjunto. 

Instalacion:
----------
Este programa esta desarrollado en java, por tanto necesita la maquina virtual java para que funcione. La maquina virtual java puede ser descargada gratuitamente desde aqui: http://www.java.com/es/download/


Ejecucion:
---------
Para ejecutar el program escribir en consola:
"java -jar dist\LibroDeEsher.jar"

O utilizar los ficheros Linux.sh o Windows.bat dependiendo del sistema operativo utilizado. Este script intentar� buscar la �ltima versi�n de java en tu sistema y ejecutarlo. 

Subida de Nivel Remota:
----------
Desde la versi�n 0.976 existe la posibilidad de que los jugadores se suban el nivel de su personaje en casa, tranquilamente y sin supervisi�n alguna. Esto se ha hecho intentando que sea la forma m�s segura posible y evitar que estos hagan trampas. Para ello, el DJ como el jugador tiene un copia del personaje en sus respectivos ordenadores. El jugador puede utilizar este programa de forma que puede gastar sus puntos de desarrollo como desee seg�n las normas establecidas. Una vez hecho, debe de exportar SOLAMENTE la subida de nivel y no todo el persoanje, y enviarlo por email al DJ. El DJ actualiza la versi�n del personaje de la que dispone en su ordenador, mostrando los puntos de desarrollo que se han gastado (y comprobar que no se han gastado de m�s) e imprimiendo la ficha despu�s. 
Con esta metodolog�a se ahorra tiempo en las partidas, reuniendo a los jugadores solamente para jugar y no para realizar modificaciones en las fichas. 


Problemas posibles
----------
Existe un problema conocido con el generador de personajes. Normalmente se debe a que los ficheros con las caracter�sticas y las habilidades se bajan en un formato extra�o (ocurre en Windows en la mayor�a de ordenadores). Si te pasa este problema (lo reconocer�s por que al abrir el fichero rolemaster/caracter�sticas.txt los acentos se ven de forma extra�a), visita la p�gina web del Libro de Esher y descargate el fichero rolemaster_win.zip y sustituye el contenido del programa por el de este fichero.


Versi�n
------------
0.98 	A�adido:
		- La puntuaci�n m�xima de las caracter�sticas ya no est� limitada a 660. 
		- Las razas pueden tener culturas por patrones: ej. Todas las Urbanas. 
		- Se permiten exportar subidas de nivel de forma segura. Esto permite a un jugador subirse el nivel en su casa y luego enviarlo al DJ para que lo confirme y lo imprima.
		Corregido:
		- Las opciones se guardan ahora en el directorio del usuario, corrigiendo un posible error de escritura. 
		- Solucionado error al generar las TRs de Enfermedades y Venenos
		
0.975	Cambios:
		- Corregido un fallo que impide seleccionar cualquier idioma con los puntos de desarrollo.
		- Al cargar un PJ antiguo, se le a�aden las nuevas categor�as que aparecen en el fichero categorias.txt si estas no exist�an cuando se gener� el personaje.
		- Ya no se pierden los puntos de desarrollo en los idiomas de la cultura. 

0.9741	Cambios:
		- Corregido un error en los talentos de personaje que impide visualizarlos correctamente. 
		- Apariencia de Windows para las ventanas si se ejecuta en Windows. Apariencia GTK para los usuarios de Linux.
		
0.974	Cambios:
		- Soluciona un fallo que impide utilizar las flechas para aumentar los rangos de las habilidades comunes.
		- Soluciona varios fallos generados al asignar habilidades generales al insertar un PJ.
		- Solucionado que los nombres de habilidades grandes desmonten algunas ventanas.
		- Los adiestramientos al insertar personaje salen ordenados alfab�ticamente.
		- Al exportar a PDF, se perd�a alguna habilidad si se necesitaba m�s de una p�gina para mostrarlas. 
		- Talentos ahora presentan un listado de habilidades para elegir si es necesario.
		- Se pueden gastar puntos de historial para obtener objetos m�gicos.
		- Las fichas en formato texto reflejan las armaduras naturales u obtenidas por los talentos.
		- Solucionado problema que impide a los adiestramientos seleccionar armas de fuego correctamente.
		
0.973	Cambios:
		- Desarrollo F�sico tiene +10 en la categor�a extra como indica el reglamento.
		- (Solucionado) Los objetos m�gicos con bonus en las categorias los pone en bonificacion por profesion.
		- (Solucionado) Los objetos imprimen las habilidades y categorias que est�n a cero. 
		- (Solucionado) Al Insertar un Personaje, al a�adir objetos no borra los bonus del anterior.
		- (Solucionado) Fallo en las habilidades al imprimir en PDF.
		- (Solucionado) Error que ignora el bonus de profesi�n a la primera categor�a de la lista.
		- (Solucionado) Ignore listas de hechizos con el mismo nombre de profesiones distintas.
		- (Solucionado) Al insertar personaje, se a�aden los hechizos de adiestramiento como b�sica (aunque no se tenga el adiestramiento).
		- Se incluye la capacidad de movimiento en las fichas y se aplican los talentos que la modifiquen.
		- (Solucionado) Error que permite subir caracteristicas con puntos de historial indefinidamente.
		- Los adiestramientos aparecen ordenados alfab�ticamente.
		- Cuando se a�aden las armas de fuego (hay 9 categorias de armas en vez de 7), las �ltimas dos categor�as de armas elegidas sin coste definido tienen el coste en rangos similar a la 7�, y no un coste de 20 PD como era antes.
		- Ahora al insertar personaje, se puede definir habilidades especializadas y generales.
		- Separaci�n de armas en generalizaciones.
		- Los talentos pueden dar una habilidad como com�n a escoger por el usuario de entre las de una categor�a.
		- Los talentos pueden incluir TRs (Esencia, Fr�o, etc.).
		- (Solucionado) Los personajes aleatorio tambi�n cogen el equipo de los adiestramientos.
		- Los mensajes de error se dividen en varias l�neas si es necesario.
		- Ahora hay habilidades que pueden ser requisitos de otras habilidades (como ocurre con los poderes chi).
		- A�ade una opci�n que permite usar el sistema de artes marciales golpes y barridos (divididos en cuatro grados) o el nuevo de la gu�a de artes marciales que es una �nica habilidad.
		
0.972	Correcci�n del error que impide leer los ficheros en Windows Vista. 

0.971	Correcci�n de bugs a la hora de generar un personaje elementalista aleatorio.

0.970	Se incluyen las reglas particulares de los elementalistas. 

0.961	Personajes aleatorios tambi�n cogen habilidades especializadas y generales.

0.960	Habilidades especializadas y generales.

0.957	Visualizaci�n gr�fica de errores.

0.956	Se guarda las opciones seleccionadas para futuros usos. Se corrigen m�s bugs.

0.955	Los adiestramientos ya incluyen hechizos.
		Los talentos se pueden restringir a razas particulares.
		Se pueden insertar talentos.
		Los hechizos oscuros pueden ser listas b�sicas.
		
0.954	Se pueden gastar puntos de historial para subir caracteristicas.
		Los adiestramientos permiten elegir caracteristica para subir.
		Los talentos permiten bonus a las caracter�sticas.
		
0.9531	Equipo de adiestramientos y su listado en las hojas de personaje.

0.953	Talentos para PJs y PNJs.

0.952	Correcci�n de bugs al guardar el personaje, correcci�n de bugs en los adiestramientos

0.951	Mejora del Rendimiento de las Ventanas

0.95	Insertar Categorias y Habilidades

0.94	A�adir m�dulos al programa,  A�adir bonus especiales a las habilidades

0.93	Inserci�n de Personajes

0.92	Correcci�n de algunos bugs

0.90	Exportar a PDF y a TXT

0.80	Personajes Aleatorios

0.71	Correcci�n de algunos bugs

0.70	Adiestramientos

0.60	Culturas


Mas informaci�n
-------------------------

http://librodeesher.sourceforge.net

Bugs, ideas, errores y demas a :
softwaremagico@gmail.com
