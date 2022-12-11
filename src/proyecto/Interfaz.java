package proyecto;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import proyecto.conexionsql.*;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

public class Interfaz extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private JPanel contentPane;
	private JTextField cedula;
	private JTextField nombre;
	private JTextField sueldo;
	private JTextField apellido;

	/**
	 * Launch the application.
	 */
	Connection conexion = null;
	PreparedStatement  ps = null;
	ResultSet rs = null;
	private JTextField id;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz frame = new Interfaz();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interfaz() {
		
		this.setLocationRelativeTo(null);
		this.getContentPane().setBackground(getBackground());
		conexion = conexionsql.conectar();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 516, 323);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 500, 284);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("C.I");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setBounds(69, 115, 33, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1.setBounds(69, 146, 46, 14);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Apellido");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1_1.setBounds(69, 179, 46, 14);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Sueldo");
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1_1_1.setForeground(new Color(0, 0, 0));
		lblNewLabel_1_1_1.setBounds(69, 210, 46, 14);
		panel.add(lblNewLabel_1_1_1);
		
		cedula = new JTextField();
		cedula.setBounds(144, 112, 109, 20);
		panel.add(cedula);
		cedula.setColumns(10);
		
		nombre = new JTextField();
		nombre.setColumns(10);
		nombre.setBounds(144, 143, 109, 20);
		panel.add(nombre);
		
		sueldo = new JTextField();
		sueldo.setColumns(10);
		sueldo.setBounds(144, 207, 109, 20);
		panel.add(sueldo);
		
		apellido = new JTextField();
		apellido.setColumns(10);
		apellido.setBounds(144, 176, 109, 20);
		panel.add(apellido);
		
		JButton btnActualizar = new JButton("Modificar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatosActualizar();
				LimpiarCasillas();
			}
		});
		btnActualizar.setBounds(302, 199, 115, 37);
		panel.add(btnActualizar);
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatosNuevos();
				LimpiarCasillas();
			}
		});
		btnGuardar.setBounds(302, 97, 115, 37);
		panel.add(btnGuardar);
		
		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatosBorrar();
				LimpiarCasillas();
			}
		});
		btnBorrar.setBounds(302, 148, 115, 37);
		panel.add(btnBorrar);
		
		id = new JTextField();
		id.setBounds(140, 46, 211, 20);
		panel.add(id);
		id.setColumns(10);
		
		JLabel ID = new JLabel("ID ");
		ID.setFont(new Font("Arial", Font.PLAIN, 12));
		ID.setForeground(new Color(0, 0, 0));
		ID.setBounds(86, 49, 46, 14);
		panel.add(ID);
		
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DatosBuscar();
			}
		});
		btnNewButton.setBounds(377, 45, 89, 23);
		panel.add(btnNewButton);
	}
	
	public void LimpiarCasillas() {
		cedula.setText("");
		nombre.setText("");
		sueldo.setText("");
		apellido.setText("");
		id.setText("");
	}
	
	
	public void DatosNuevos() {
		try {
			ps = conexion.prepareStatement("Insert into datos (Nombre,Apellido,sueldo,cedula) values (?,?,?,?)");
			
			ps.setString(1, nombre.getText());
			ps.setString(4, cedula.getText());
			ps.setString(2, apellido.getText());
			ps.setDouble(3, Double.parseDouble(sueldo.getText()));
			
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Registro de datos Exitoso");
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Registro de datos Fallido, Error: "+e.getMessage());
		}
	}
	
	public void DatosBuscar() {
		try {
			
			ps = conexion.prepareStatement("Select Nombre,Apellido,sueldo,cedula from datos where id=?");
			
			ps.setString(1, id.getText());
			
			rs= ps.executeQuery();
			
			if(rs.next()) {
				cedula.setText(rs.getString("cedula"));
				nombre.setText(rs.getString("nombre"));
				apellido.setText(rs.getString("apellido"));
				sueldo.setText(rs.getString("sueldo"));
			}else {
			
			JOptionPane.showMessageDialog(null, "Registro no encontrado");
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocurrio un error al acceder a la base de datos, Error: "+e.getMessage());
		}
	}
	
	
	public void DatosActualizar() {
		try {
			
			ps = conexion.prepareStatement("UPDATE datos SET nombre=?,apellido=?,sueldo=?,cedula=? where id=?");
			
			
			ps.setString(4, cedula.getText());
			ps.setString(1, nombre.getText());
			ps.setString(2, apellido.getText());
			ps.setDouble(3, Double.parseDouble(sueldo.getText()));
			ps.setString(5, id.getText());
			
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Modificacion de datos Exitosa");
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Modificacion de datos Fallida, Error: "+e.getMessage());
		}
	}
	
	public void DatosBorrar() {
		try {
			
			ps = conexion.prepareStatement("Delete from datos where id=?");
			
			ps.setString(1, id.getText());
			
			ps.execute();
			
			JOptionPane.showMessageDialog(null, "Registro eliminado correctamente");
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "No se pudo eliminar el registro, Error: "+e.getMessage());
		}
	}
	
}
