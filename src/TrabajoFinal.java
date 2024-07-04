import java.util.Scanner;

public class TrabajoFinal {
  // Tipos de roles de usuario
  static final String ROLE_1 = "Administrador";
  static final String ROLE_2 = "Empleado";
  static final String ROLE_3 = "Analista";

  // Tipos de categorias de producto
  static final String CATEGORY_1 = "Respuestos Mecanicos";
  static final String CATEGORY_2 = "Respuestos Electrico";
  static final String CATEGORY_3 = "Equipo de Protección Personal";
  static final String CATEGORY_4 = "Otros";

  // Tipos de atributos del producto
  static final String ATTRIBUTE_1 = "Mas vendidos";
  static final String ATTRIBUTE_2 = "Ventas bajas";

  // Arrays simulando base de datos Products
  static int[] productCodes = new int[40];
  static String[] productNames = new String[40];
  static int[] productQtys = new int[40];
  static String[] productCategories = new String[40];
  static String[] productAttributes = new String[40];
  static int productCount = 0;

  // Arrays simulando base de datos Users
  static int[] userCodes = {101, 102, 103};
  static String[] userNames = {"Saul", "Juan", "Edgar"};
  static String[] userRoles = {ROLE_1, ROLE_2, ROLE_3};
  static int userCount = 3;

  public static void main(String[] args) {
    Scanner SCA = new Scanner(System.in);
    int userIndex = -1;
    int userCode;
    String userRole;

    // Solicitar código de usuario
    while (userIndex == -1) {
      System.out.print("Ingrese su código de usuario para ingresar: ");
      userCode = SCA.nextInt();
      userIndex = authUser(userCode);
    }
    userRole = userRoles[userIndex];

    // Productos de inicio
    System.out.println("\n...Cargando productos");
    addProduct(101, "DISPERSOR DE CALOR", 40, CATEGORY_1, ATTRIBUTE_1);
    addProduct(102, "Bomba de aceite de moto", 80, CATEGORY_1, ATTRIBUTE_2);
    addProduct(103, "PanelBoards", 80, CATEGORY_2, ATTRIBUTE_2);
    addProduct(104, "Cable LSOH-90", 80, CATEGORY_2, ATTRIBUTE_1);
    addProduct(105, "Casco SteelPro", 80, CATEGORY_3, ATTRIBUTE_1);
    addProduct(106, "Tapón de Oído", 80, CATEGORY_3, ATTRIBUTE_1);

    boolean exit = false;
    // Que acción va a realizar el usuario
    while (!exit) {
      System.out.println("\nSeleccione una acción:");
      System.out.println("1. Crear producto");
      System.out.println("2. Buscar producto");
      System.out.println("3. Generar reporte");
      System.out.println("4. Salir de la app");
      System.out.print("Ingrese su elección: ");
      int choice = SCA.nextInt();

      switch (choice) {
        // Crear producto [Administrador]
        case 1: {
          if (userRole.equals(ROLE_1)) {
            handleAddProduct();
          } else {
            System.out.println("No tiene los permisos para crear productos");
          }
          break;
        }
        // Buscar producto [Administrador , Empleado, Analista]
        case 2: {
          System.out.print("\nIngrese el codigo del producto a buscar: ");
          int code = SCA.nextInt();
          int index = searchProduct(code);

          if (userRole.equals(ROLE_1) || userRole.equals(ROLE_2)) {
            handleSearchActions(index); // Modificar stock [Administrador, Empleado]
          } else {
            System.out.println("No tiene los permisos para editar los productos");
          }
          break;
        }
        // Generar Reporte [Administrador, Analista]
        case 3: {
          if (userRole.equals(ROLE_1) || userRole.equals(ROLE_3)) {
            handleReportsActions();
          } else {
            System.out.println("No tiene los permisos para generar reportes");
          }
          break;
        }
        // Salir
        case 4: {
          exit = true;
          System.out.println("...Saliendo de la app");
          break;
        }
        default:
          System.out.println("Error: Esa acción no existe");
      }

    }
  }

  // Método para autenticar al usuario
  public static int authUser(int code) {
    for (int i = 0; i < userCount; i++) {
      if (userCodes[i] == code) {
        System.out.println("\nBienvenido, " + userNames[i] + " - " + userRoles[i]);
        switch (userRoles[i]) {
          case ROLE_1:
            System.out.println("Acceso a todas las funcionalidades otorgadas");
            break;
          case ROLE_2:
            System.out.println("Acceso a la gestión de stock y los reportes");
            break;
          case ROLE_3:
            System.out.println("Acceso a la generación de reportes");
            break;
          default:
            System.out.println("Rol desconocido");
            break;
        }
        return i;
      }
    }
    System.out.println("\nError: Usuario no encontrado");
    return -1;
  }

  public static void handleAddProduct() {
    Scanner SCA = new Scanner(System.in);

    System.out.print("\nIngrese el codigo del producto: ");
    int code = SCA.nextInt();
    SCA.nextLine();

    System.out.print("Ingrese el nombre del producto: ");
    String name = SCA.nextLine();

    System.out.print("Ingrese cantidad del producto: ");
    int qty = SCA.nextInt();
    SCA.nextLine();

    System.out.print("Ingrese la categoria del producto (1: Respuestos Mecanicos, 2: Respuestos Electricos, 3: Equipo de Protección Personal, 4: Otros): ");
    int category = SCA.nextInt();
    SCA.nextLine();

    System.out.print("Ingresa el atributo del producto (1: Mas vendidos, 2: Ventas bajas): ");
    int attribute = SCA.nextInt();
    SCA.nextLine();

    addProduct(code, name, qty, getCategoryValue(category), getAttributeValue(attribute));
  }

  public static void handleSearchActions(int productIndex) {
    Scanner SCA = new Scanner(System.in);

    System.out.println("\nSeleccione una acción:");
    System.out.println("1. Agregar stock");
    System.out.println("2. Retirar stock");
    System.out.println("3. Volver al menú principal");
    System.out.print("Ingrese su elección: ");
    int searchChoice = SCA.nextInt();

    switch (searchChoice) {
      case 1:
        System.out.print("Ingrese la cantidad a agregar: ");
        int addQty = SCA.nextInt();
        addStock(productIndex, addQty);
        break;
      case 2:
        System.out.print("Ingrese la cantidad a retirar: ");
        int removeQty = SCA.nextInt();
        removeStock(productIndex, removeQty);
        break;
      case 3:
        return;
      default:
        System.out.println("Error: Opción inválida");
    }
  }

  public static void handleReportsActions() {
    Scanner SCA = new Scanner(System.in);

    System.out.println("\nSeleccione el reporte que quiere generar:");
    System.out.println("1. Todos los productos");
    System.out.println("2. Productos mas vendidos");
    System.out.println("3. Productos menos vendidos");
    System.out.println("4. Por categoria");
    System.out.print("Ingrese su elección: ");
    int searchChoice = SCA.nextInt();

    switch (searchChoice) {
      case 1:
        displayAllProducts();
        break;
      case 2:
        displayTopSellingProducts();
        break;
      case 3:
        displayLeastSellingProducts();
        break;
      case 4:
        System.out.print("Ingrese la categoria (1: Respuestos Mecanicos, 2: Respuestos Electricos, 3: Equipo de Protección Personal, 4: Otros): ");
        int category = SCA.nextInt();
        SCA.nextLine();
        String categoryTxt = getCategoryValue(category);
        displayProductsByCategory(categoryTxt);
        break;
      default:
        System.out.println("Error: Opción inválida");
    }
  }

  public static void addProduct(int code, String name, int qty, String category, String attribute) {
    if (productCount >= productCodes.length) {
      System.out.println("\nError: No hay espacio para agregar al producto.");
      return;
    }
    for (int i = 0; i < productCount; i++) {
      if (productCodes[i] == code) {
        System.out.println("\nError: Ya existe un producto con este código");
        return;
      }
    }
    productCodes[productCount] = code;
    productNames[productCount] = name;
    productQtys[productCount] = qty;
    productCategories[productCount] = category;
    productAttributes[productCount] = attribute;
    productCount = productCount + 1;
    System.out.println("\nProducto añadido exitosamente.");
  }

  public static int searchProduct(int code) {
    for (int i = 0; i < productCount; i++) {
      if (productCodes[i] == code) {
        System.out.println("Producto encontrado:");
        System.out.printf("Codigo: %d, Nombre: %s, Cantidad: %d unidades, Categoria: %s, Atributo: %s\n", productCodes[i], productNames[i], productQtys[i], productCategories[i], productAttributes[i]);
        return i;
      }
    }
    System.out.println("Error: Producto no encontrado.");
    return -1;
  }

  public static void addStock(int index, int stockToAdd) {
    productQtys[index] = productQtys[index] + stockToAdd;
    System.out.println("Stock del producto " + productNames[index] + " actualizado (" + productQtys[index] + ")");
  }

  public static void removeStock(int index, int stockToRemove) {
    if (productQtys[index] >= stockToRemove) {
      productQtys[index] = productQtys[index] - stockToRemove;
      System.out.println("Stock del producto " + productNames[index] + " actualizado (" + productQtys[index] + ")");
      return;
    }
    System.out.println("Error: No hay suficientes productos");
  }

  public static void displayAllProducts() {
    System.out.println("\nInventario - Todos los productos: ");
    if (productCount == 0) {
      System.out.println("No hay productos en el inventario");
      return;
    }
    for (int i = 0; i < productCount; i++) {
      System.out.printf("Codigo: %d, Nombre: %s, Cantidad: %d unidades, Categoria: %s , Atributo: %s\n", productCodes[i], productNames[i], productQtys[i], productCategories[i], productAttributes[i]);
    }
  }

  public static void displayProductsByCategory(String category) {
    System.out.println("Inventario - " + category + ": ");
    if (productCount == 0) {
      System.out.println("No hay productos en el inventario");
      return;
    }
    for (int i = 0; i < productCount; i++) {
      if (productCategories[i].equals(category)) {
        System.out.printf("Codigo: %d, Nombre: %s, Cantidad: %d unidades, Categoria: %s , Atributo: %s\n", productCodes[i], productNames[i], productQtys[i], productCategories[i], productAttributes[i]);
      }
    }
  }

  public static void displayTopSellingProducts() {
    System.out.println("Inventario - Productos mas vendidos: ");
    if (productCount == 0) {
      System.out.println("No hay productos en el inventario");
      return;
    }
    for (int i = 0; i < productCount; i++) {
      if (productAttributes[i].equals(ATTRIBUTE_1)) {
        System.out.printf("Codigo: %d, Nombre: %s, Cantidad: %d unidades, Categoria: %s , Atributo: %s\n", productCodes[i], productNames[i], productQtys[i], productCategories[i], productAttributes[i]);
      }
    }
  }

  public static void displayLeastSellingProducts() {
    System.out.println("Inventario - Productos menos vendidos: ");
    if (productCount == 0) {
      System.out.println("No hay productos en el inventario");
      return;
    }
    for (int i = 0; i < productCount; i++) {
      if (productAttributes[i].equals(ATTRIBUTE_2)) {
        System.out.printf("Codigo: %d, Nombre: %s, Cantidad: %d unidades, Categoria: %s , Atributo: %s\n", productCodes[i], productNames[i], productQtys[i], productCategories[i], productAttributes[i]);
      }
    }
  }

  public static String getCategoryValue(int categoryInt) {
    String categoryTxt = "Error";
    if(categoryInt == 1) {
      categoryTxt = CATEGORY_1;
    } else if (categoryInt == 2) {
      categoryTxt = CATEGORY_2;
    } else if (categoryInt == 3) {
      categoryTxt = CATEGORY_3;
    } else if (categoryInt == 4) {
      categoryTxt = CATEGORY_4;
    }
    return categoryTxt;
  }

  public static String getAttributeValue(int attributeInt) {
    String attributeTxt = "Error";
    if(attributeInt == 1) {
      attributeTxt = ATTRIBUTE_1;
    } else if (attributeInt == 2) {
      attributeTxt = ATTRIBUTE_2;
    }
    return attributeTxt;
  }
}