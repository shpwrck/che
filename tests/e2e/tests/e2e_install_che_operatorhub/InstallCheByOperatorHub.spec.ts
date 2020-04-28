/*********************************************************************
 * Copyright (c) 2019 Red Hat, Inc.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 **********************************************************************/

import { e2eContainer } from '../../inversify.config';
import { CLASSES } from '../../inversify.types';
import { OcpWebConsolePage } from '../../pageobjects/openshift/OcpWebConsolePage';
import { TestConstants } from '../../TestConstants';

const ocpWebConsole: OcpWebConsolePage = e2eContainer.get(CLASSES.OcpWebConsolePage);

suite('E2E', async () => {

    suite('Create new Che cluster', async () => {
        test('Click on the logo-name Che operator', async () => {
            await ocpWebConsole.clickOnInstalledOperatorLogoName();
            await ocpWebConsole.waitOverviewCsvOperator();
        });

        test('Click on the Create New, wait CSV yaml', async () => {
            await ocpWebConsole.clickCreateNewCheClusterLink();
            await ocpWebConsole.waitCreateCheClusterYaml();
        });

        test('Create Che Cluster ', async () => {
            await ocpWebConsole.clickOnCreateCheClusterButton();
            await ocpWebConsole.waitResourcesCheClusterTitle();
            await ocpWebConsole.clickOnCheClusterResourcesName();
        });
    });

    suite('Check the Che is ready', async () => {
        test('Wait Keycloak Admin Console URL', async () => {
            await ocpWebConsole.waitKeycloakAdminConsoleUrl();
        });

        test('Wait installed application URL', async () => {
            await ocpWebConsole.waitInstalledAppUrl();
        });
    });

});
