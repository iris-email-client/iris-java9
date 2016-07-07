package br.unb.cic.iris.persistence.jdbc.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.persistence.EmailDAO;
import br.unb.cic.iris.persistence.IrisPersistenceException;

public class EmailDaoJdbc extends AbstractDaoJdbc implements EmailDAO {
	private static final String SELECT_MESSAGES_BY_ID = "SELECT * FROM message WHERE id = ?";
	private static final String SELECT_MESSAGES_BY_FOLDER_ID = "SELECT * FROM message WHERE folderid = ?";
	private static final String INSERT_MESSAGE = "INSERT INTO message (id, _from, _to, cc, bcc, subject, message, _date, folderid) VALUES (?,?,?,?,?,?,?,?,?)";

	@Override
	public void saveMessage(EmailMessage message) throws IrisPersistenceException {
		message.setId(UUID.randomUUID().toString());
		try (Connection conn = getDbUtil().connect();
                PreparedStatement pstmt = conn.prepareStatement(INSERT_MESSAGE)) {
            pstmt.setString(1, message.getId());
            pstmt.setString(2, message.getFrom());
            pstmt.setString(3, message.getTo());
            pstmt.setString(4, message.getCc());
            pstmt.setString(5, message.getBcc());
            pstmt.setString(6, message.getSubject());
            pstmt.setString(7, message.getMessage());
            pstmt.setTimestamp(8, new Timestamp(message.getDate().getTime()));
            pstmt.setString(9, message.getFolder().getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	throw new IrisPersistenceException("Could not create message: "+e.getMessage(), e);
        }
	}

	@Override
	public Date lastMessageReceived(String folderName) throws IrisPersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmailMessage> listMessages(String idFolder) throws IrisPersistenceException {
		List<EmailMessage> messages = new LinkedList<>();
		try (Connection conn = getDbUtil().connect();
				PreparedStatement pstmt = conn.prepareStatement(SELECT_MESSAGES_BY_FOLDER_ID)) {
			
			pstmt.setString(1, idFolder);    
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				messages.add(toEmailMessage(rs));
			}			
			
			rs.close();
		} catch (SQLException e) {
			throw new IrisPersistenceException("Could not list all folders: "+e.getMessage(), e);
		}
		return messages;
	}

	@Override
	public EmailMessage findById(String id) throws IrisPersistenceException {
		EmailMessage message = null;
		try (Connection conn = getDbUtil().connect();
                PreparedStatement pstmt = conn.prepareStatement(SELECT_MESSAGES_BY_ID)) {
            pstmt.setString(1, id);           
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
            	message = toEmailMessage(rs);
            }
            rs.close();
        } catch (SQLException e) {
        	throw new IrisPersistenceException("Could not find message with id '"+id+"': "+e.getMessage(), e);
        }
		return message;
	}

	private EmailMessage toEmailMessage(ResultSet rs) throws SQLException, IrisPersistenceException{
		EmailMessage message = getEntityFactory().createEmailMessage();
		message.setId(rs.getString("id"));
		message.setFrom(rs.getString("_from"));
		message.setTo(rs.getString("_to"));
		message.setCc(rs.getString("cc"));
		message.setBcc(rs.getString("bcc"));
		message.setSubject(rs.getString("subject"));
		message.setMessage(rs.getString("message"));
		message.setDate(rs.getDate("_date"));
		
		String folderId = rs.getString("folderid");
		message.setFolder(getFolderDAO().findById(folderId));
				
		return message;
	}
	
}