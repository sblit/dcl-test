package org.dclayer.test;
import java.util.LinkedList;

import junit.framework.TestCase;

import org.dclayer.exception.net.buf.BufException;
import org.dclayer.exception.net.parse.ParseException;
import org.dclayer.net.Data;
import org.dclayer.net.PacketComponentI;
import org.dclayer.net.buf.DataByteBuf;
import org.dclayer.net.component.ArrayPacketComponent;
import org.dclayer.net.component.DataComponent;
import org.junit.Test;



public class ArrayPacketComponentTest extends TestCase {
	
	private ArrayPacketComponent<DataComponent> arrayPacketComponent;
	
	@Override
	protected void setUp() throws Exception {
		arrayPacketComponent = make();
	}
	
	@Test
	public void testInternalElements() throws BufException, ParseException {
		
		Data[] data = new Data[] {
				new Data("0123456789abcdef"),
				new Data("123456789abcdef0"),
				new Data("23456789abcdef01"),
				new Data("3456789abcdef012"),
				new Data("456789abcdef0123")
		};
		
		arrayPacketComponent.setElements(5);
		
		int i = 0;
		for(DataComponent dataComponent : arrayPacketComponent) {
			dataComponent.setData(data[i++]);
		}
		
		DataByteBuf dataByteBuf = write(arrayPacketComponent);
		dataByteBuf.seek(0);
		
		ArrayPacketComponent<DataComponent> arrayPacketComponent = read(dataByteBuf);
		
		i = 0;
		for(DataComponent dataComponent : arrayPacketComponent) {
			if(!data[i].equals(dataComponent.getData())) {
				fail(String.format("index %d: %s != %s", i, dataComponent.getData(), data[i]));
			}
			i++;
		}
		
	}
	
	@Test
	public void testExternalElements() throws BufException, ParseException {
		
		Data[] data = new Data[] {
				new Data("0123456789abcdef"),
				new Data("123456789abcdef0"),
				new Data("23456789abcdef01"),
				new Data("3456789abcdef012"),
				new Data("456789abcdef0123")
		};
		
		LinkedList<DataComponent> dataComponents = new LinkedList<>();
		
		for(Data d : data) {
			DataComponent dataComponent = new DataComponent();
			dataComponent.setData(d);
			dataComponents.add(dataComponent);
		}
		
		arrayPacketComponent.setElements(dataComponents);
		
		DataByteBuf dataByteBuf = write(arrayPacketComponent);
		dataByteBuf.seek(0);
		
		ArrayPacketComponent<DataComponent> arrayPacketComponent = read(dataByteBuf);
		
		int i = 0;
		for(DataComponent dataComponent : arrayPacketComponent) {
			if(!data[i].equals(dataComponent.getData())) {
				fail(String.format("index %d: %s != %s", i, dataComponent.getData(), data[i]));
			}
			i++;
		}
		
	}
	
	@Test
	public void testLength() throws BufException {
		
		Data[] data = new Data[] {
				new Data("0123456789abcdef"),
				new Data("123456789abcdef0"),
				new Data("23456789abcdef01"),
				new Data("3456789abcdef012"),
				new Data("456789abcdef0123")
		};
		
		LinkedList<DataComponent> dataComponents = new LinkedList<>();
		
		for(Data d : data) {
			DataComponent dataComponent = new DataComponent();
			dataComponent.setData(d);
			dataComponents.add(dataComponent);
		}
		
		arrayPacketComponent.setElements(dataComponents);
		
		int length = arrayPacketComponent.length();
		
		DataByteBuf dataByteBuf = write(arrayPacketComponent);
		
		if(dataByteBuf.getData().length() != length) {
			fail(String.format("length: predicted %d, actually %d bytes", length, dataByteBuf.getData().length()));
		}
		
	}
	
	@Test
	public void testNumElements() throws BufException, ParseException {
		
		Data[] data = new Data[] {
				new Data("0123456789abcdef"),
				new Data("123456789abcdef0"),
				new Data("23456789abcdef01"),
				new Data("3456789abcdef012"),
				new Data("456789abcdef0123")
		};
		
		arrayPacketComponent.setElements(5);
		
		int i = 0;
		for(DataComponent dataComponent : arrayPacketComponent) {
			dataComponent.setData(data[i++]);
		}
		
		arrayPacketComponent.setElements(3);
		
		DataByteBuf dataByteBuf = write(arrayPacketComponent);
		dataByteBuf.seek(0);
		
		ArrayPacketComponent<DataComponent> arrayPacketComponent = read(dataByteBuf);
		
		i = 0;
		for(DataComponent dataComponent : arrayPacketComponent) {
			if(!data[i].equals(dataComponent.getData())) {
				fail(String.format("index %d: %s != %s", i, dataComponent.getData(), data[i]));
			}
			if(i > 2) {
				fail("read too many elements");
			}
			i++;
		}
		
	}
	
	//
	
	private ArrayPacketComponent<DataComponent> make() {
		return new ArrayPacketComponent<DataComponent>() {
			@Override
			protected DataComponent newElementPacketComponent() {
				return new DataComponent();
			}
		};
	}
	
	private DataByteBuf write(PacketComponentI packetComponent) throws BufException {
		
		DataByteBuf dataByteBuf = new DataByteBuf(Data.GROW);
		dataByteBuf.write(packetComponent);
		return dataByteBuf;
		
	}
	
	private ArrayPacketComponent<DataComponent> read(DataByteBuf dataByteBuf) throws ParseException, BufException {
		
		ArrayPacketComponent<DataComponent> arrayPacketComponent = make();
		
		arrayPacketComponent.read(dataByteBuf);
		
		return arrayPacketComponent;
		
	}

}
