/**
 * <p/>
 * Copyright 2003-2006 Jive Software.
 * <p/>
 * All rights reserved. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jivesoftware.smackx.jingle.mediaimpl.sshare;

import org.jivesoftware.smackx.jingle.JingleSession;
import org.jivesoftware.smackx.jingle.media.JingleMediaManager;
import org.jivesoftware.smackx.jingle.media.JingleMediaSession;
import org.jivesoftware.smackx.jingle.media.PayloadType;
import org.jivesoftware.smackx.jingle.mediaimpl.sshare.api.ImageDecoder;
import org.jivesoftware.smackx.jingle.mediaimpl.sshare.api.ImageEncoder;
import org.jivesoftware.smackx.jingle.nat.JingleTransportManager;
import org.jivesoftware.smackx.jingle.nat.TransportCandidate;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a JingleMediaManager for ScreenSharing.
 * It currently uses an Audio payload Type. Which needs to be fixed in the next version.
 *
 * @author Thiago Camargo
 */

public class ScreenShareMediaManager extends JingleMediaManager {

    public static final String MEDIA_NAME = "ScreenShare";

    private List<PayloadType> payloads = new ArrayList<PayloadType>();

    private ImageDecoder decoder = null;
    private ImageEncoder encoder = null;

    public ScreenShareMediaManager(JingleTransportManager transportManager) {
        super(transportManager);
        setupPayloads();
    }

    /**
     * Setup API supported Payloads
     */
    private void setupPayloads() {
        payloads.add(new PayloadType.Audio(30, "sshare"));
    }

    /**
     * Return all supported Payloads for this Manager.
     *
     * @return The Payload List
     */
    public List<PayloadType> getPayloads() {
        return payloads;
    }

    /**
     * Returns a new JingleMediaSession
     *
     * @param payloadType payloadType
     * @param remote      remote Candidate
     * @param local       local Candidate
     * @return JingleMediaSession JingleMediaSession
     */
    public JingleMediaSession createMediaSession(PayloadType payloadType, final TransportCandidate remote, final TransportCandidate local, final JingleSession jingleSession) {
        ScreenShareSession session = null;
        session = new ScreenShareSession(payloadType, remote, local, "Screen", jingleSession);
        if (encoder != null) {
            session.setEncoder(encoder);
        }
        if (decoder != null) {
            session.setDecoder(decoder);
        }
        return session;
    }

    public PayloadType getPreferredPayloadType() {
        return super.getPreferredPayloadType();
    }

    public ImageDecoder getDecoder() {
        return decoder;
    }

    public void setDecoder(ImageDecoder decoder) {
        this.decoder = decoder;
    }

    public ImageEncoder getEncoder() {
        return encoder;
    }

    public void setEncoder(ImageEncoder encoder) {
        this.encoder = encoder;
    }
    
    public  String getName() {
        return MEDIA_NAME;
    }
}
