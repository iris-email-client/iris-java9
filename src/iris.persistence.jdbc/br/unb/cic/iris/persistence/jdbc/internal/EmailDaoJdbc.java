package br.unb.cic.iris.persistence.jdbc.internal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.unb.cic.iris.model.EmailMessage;
import br.unb.cic.iris.persistence.IEmailDAO;
import br.unb.cic.iris.persistence.PersistenceException;

public class EmailDaoJdbc extends AbstractDaoJdbc implements IEmailDAO {
	private static final String SELECT_MESSAGES_BY_FOLDER_ID = "SELECT * FROM message WHERE folderid = ?";
	private static final String INSERT_MESSAGE = "INSERT INTO message (id, _from, _to, cc, bcc, subject, message, _date, folderid) VALUES (?,?,?,?,?,?,?,?,?)";

	@Override
	public void saveMessage(EmailMessage message) throws PersistenceException {
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
        	throw new PersistenceException("Could not create message: "+e.getMessage(), e);
        }
	}

	@Override
	public Date lastMessageReceived() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmailMessage> listMessages(String idFolder) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

}
