import java.io.RandomAccessFile;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JTextArea;

public class Ranking {

    protected static String[] nombres=new String[10];
    protected static int[] puntos=new int[10];
    protected static int[] niveles=new int[10];
    protected static String[] fechas=new String[10];
    protected  int lineas=0;

    public void escribirRanking(String nombre, int puntaje, int nivel) {
        String aux_nombre;
        int aux_puntaje;
        int aux_nivel;
        String aux_fecha;
        Calendar fecha=new GregorianCalendar();
        String fecha_actual = fecha.get(Calendar.DAY_OF_MONTH)+"/"+(fecha.get(Calendar.MONTH)+1)+"/"+fecha.get(Calendar.YEAR);
        boolean inserto=false;

        try {
            RandomAccessFile datos = new RandomAccessFile("ranking.txt", "rw");
            datos.seek(0);
            while(datos.readLine() != null){
                lineas++;
            }
            datos.seek(0);
            for(int i=0;i<lineas;i++){
                String renglon=datos.readLine();
                String palabras[]=renglon.split("-");
                nombres[i]=palabras[0];
                puntos[i]=Integer.parseInt(palabras[1]);
                niveles[i]=Integer.parseInt(palabras[2]);
                fechas[i]=palabras[3];
            }
            //INSERTAR ORDENADO Y ESCRIBIR
            if(lineas==0){
                nombres[0] = nombre;
                puntos[0] = puntaje;
                niveles[0] = nivel;
                fechas[0] = fecha_actual;
            }else{
                if(puntaje>=puntos[lineas-1]){
                    for(int i=0;i < lineas && inserto == false;i++){
                        if(puntaje>=puntos[i]){
                            aux_nombre = nombres[i];
                            aux_puntaje = puntos[i];
                            aux_nivel = niveles[i];
                            aux_fecha = fechas[i];
                            nombres[i] = nombre;
                            puntos[i] = puntaje;
                            niveles[i] = nivel;
                            fechas[i] = fecha_actual;
                            inserto = true;
                            for(int j=lineas-1;j>i;j--){
                                nombres[j]=nombres[j-1];
                                puntos[j]=puntos[j-1];
                                niveles[j]=niveles[j-1];
                                fechas[j]=fechas[j-1];
                            }
                            nombres[i+1]=aux_nombre;
                            puntos[i+1]=aux_puntaje;
                            niveles[i+1]=aux_nivel;
                            fechas[i+1]=aux_fecha;
                        }
                    }
                    //AHORA QUE YA INSERTE TENGO QUE ESCRIBIR EL ARCHIVO
                }else{
                    if(lineas<10){
                        nombres[lineas] = nombre;
                        puntos[lineas] = puntaje;
                        niveles[lineas] = nivel;
                        fechas[lineas] = fecha_actual;
                    }
                    
                }
            }
            datos.seek(0);
            for(int i=0;i<10;i++){
                if(nombres[i]!=null){
                    datos.writeBytes(nombres[i]+"-");
                    datos.writeBytes(puntos[i]+"-");
                    datos.writeBytes(niveles[i]+"-");
                    datos.writeBytes(fechas[i]+"\n");
                }
            }
            datos.close();
        } catch (Exception e) {
            System.out.println("ERROR AL ESCRIBIR EL RANKING");
            System.out.println(e);
        }
    }

    public void mostrarRank(JTextArea jta){
        int cont = 0;
        String linea;
        try {
            RandomAccessFile datos = new RandomAccessFile("ranking.txt", "r");
            while((linea = datos.readLine()) != null && cont<10){
                jta.append(linea + "\n");
                cont++;
            }
            datos.close();
        }catch (Exception e) {}
    }

}