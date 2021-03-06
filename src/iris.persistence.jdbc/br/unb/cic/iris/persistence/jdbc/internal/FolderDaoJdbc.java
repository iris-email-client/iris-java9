package br.unb.cic.iris.persistence.jdbc.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import br.unb.cic.iris.model.IrisFolder;
import br.unb.cic.iris.persistence.EntityValidator;
import br.unb.cic.iris.persistence.FolderDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public class FolderDaoJdbc extends AbstractDaoJdbc implements FolderDAO {
	private static final String SELECT_FOLDER_ALL = "SELECT id, name FROM folder";
	private static final String SELECT_FOLDER_BY_NAME = "SELECT id, name FROM folder WHERE name = ?";
	private static final String SELECT_FOLDER_BY_ID = "SELECT id, name FROM folder WHERE id = ?";
	private static final String INSERT_FOLDER = "INSERT INTO folder (id, name) VALUES (?,?)";

	@Override
	public IrisFolder<?> createFolder(String folderName) throws IrisPersistenceException {
		EntityValidator.checkFolderBeforeCreate(this, folderName);
		
		IrisFolder<?> folder = getEntityFactory().createIrisFolder();
		folder.setId(UUID.randomUUID().toString());
		folder.setName(folderName);
		
		try (Connection conn = getDbUtil().connect();
                PreparedStatement pstmt = conn.prepareStatement(INSERT_FOLDER)) {
            pstmt.setString(1, folder.getId());
            pstmt.setString(2, folder.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	throw new IrisPersistenceException("Could not create folder: "+e.getMessage(), e);
        }
		
		return folder;
	}

	@Override
	public IrisFolder<?> findByName(String folderName) throws IrisPersistenceException {		
		return executeQuery(SELECT_FOLDER_BY_NAME, folderName);
	}

	@Override
	public IrisFolder<?> findById(String id) throws IrisPersistenceException {		
		return executeQuery(SELECT_FOLDER_BY_ID, id);
	}	
	
	@Override
	@SuppressWarnings("rawtypes")
	public List<IrisFolder> findAll() throws IrisPersistenceException {
		List<IrisFolder> folders = new LinkedList<>();
		try (Connection conn = getDbUtil().connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(SELECT_FOLDER_ALL)) {
			
			while (rs.next()) {
				folders.add(toFolder(rs));
			}
		} catch (SQLException e) {
			throw new IrisPersistenceException("Could not list all folders: "+e.getMessage(), e);
		}
		return folders;
	}
	
	private IrisFolder<?> executeQuery(String sql, String value) throws IrisPersistenceException {
		try (Connection conn = getDbUtil().connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, value);            
            try(ResultSet rs = pstmt.executeQuery()){
	            if(rs.next()){
	            	return toFolder(rs);
	            }
            } catch (SQLException e) {
            	throw e;//new IrisPersistenceException("Could not execute query: "+e.getMessage(), e);
            }
        } catch (SQLException e) {
        	throw new IrisPersistenceException("Could not execute query: "+e.getMessage(), e);
        }
		return null;
	}
	
	private IrisFolder<?> toFolder(ResultSet rs) throws SQLException{
		IrisFolder<?> folder = getEntityFactory().createIrisFolder();
		folder.setId(rs.getString("id"));
		folder.setName(rs.getString("name"));
		return folder;
	}


}